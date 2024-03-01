package com.owtoph.tictactoerib.root.loggedin.tictactoe

import com.owtoph.tictactoerib.root.UserName
import com.owtoph.tictactoerib.root.loggedin.tictactoe.Board.MarkerType
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [TicTacToeScope].  */
@RibInteractor
class TicTacToeInteractor :
    Interactor<TicTacToeInteractor.TicTacToePresenter, TicTacToeRouter>() {

    @Inject lateinit var board: Board

    @Inject lateinit var listener: Listener

    @Inject lateinit var presenter: TicTacToePresenter

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    @Inject
    @Named("player_one")
    lateinit var playerOne: UserName

    @Inject
    @Named("player_two")
    lateinit var playerTwo: UserName

    private var currentPlayer = MarkerType.CROSS

    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter
            .squareClicks()
            .subscribe { xy ->
                if (board.cells[xy.getX()][xy.getY()] == null) {
                    if (currentPlayer == MarkerType.CROSS) {
                        board.cells[xy.getX()][xy.getY()] = MarkerType.CROSS
                        board.currentRow = xy.getX()
                        board.currentCol = xy.getY()
                        presenter.addCross(xy)
                        currentPlayer = MarkerType.NOUGHT
                    } else {
                        board.cells[xy.getX()][xy.getY()] = MarkerType.NOUGHT
                        board.currentRow = xy.getX()
                        board.currentCol = xy.getY()
                        presenter.addNought(xy)
                        currentPlayer = MarkerType.CROSS
                    }
                }
                if (board.hasWon(MarkerType.CROSS)) {
                    presenter.setPlayerWon(playerOne.getUserName())
                } else if (board.hasWon(MarkerType.NOUGHT)) {
                    presenter.setPlayerWon(playerTwo.getUserName())
                } else if (board.isDraw()) {
                    presenter.setPlayerTie()
                } else {
                    updateCurrentPlayer()
                }
            }?.let {
                compositeDisposable.add(
                    it
                )
            }
        updateCurrentPlayer()
    }

    private fun updateCurrentPlayer() {
        if (currentPlayer == MarkerType.CROSS) {
            presenter.setCurrentPlayerName(playerOne.getUserName())
        } else {
            presenter.setCurrentPlayerName(playerTwo.getUserName())
        }
    }

    /** Presenter interface implemented by this RIB's view.  */
    interface TicTacToePresenter {
        fun squareClicks(): Observable<BoardCoordinate>
        fun setCurrentPlayerName(currentPlayer: String?)
        fun setPlayerWon(playerName: String?)
        fun setPlayerTie()
        fun addCross(xy: BoardCoordinate?)
        fun addNought(xy: BoardCoordinate?)
    }


    interface Listener {
        /**
         * Called when the game is over.
         *
         * @param winner player that won, or null if it's a tie.
         */
        fun gameWon(winner: UserName)
    }

}