package com.owtoph.tictactoerib.root.loggedin

import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameInteractor
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.annotations.Nullable
import javax.inject.Inject


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [LoggedInScope].  */
@RibInteractor
class LoggedInInteractor : Interactor<EmptyPresenter, LoggedInRouter>() {
    @Inject
    @LoggedInBuilder.LoggedInInternal
    lateinit var scoreStream: MutableScoreStream
    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        // when first logging in we should be in the OffGame state
        router.attachOffGame()
    }

    override fun willResignActive() {
        super.willResignActive()

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

    internal inner class OffGameListener : OffGameInteractor.Listener {
        override fun onStartGame() {
            router.detachOffGame()
            router.attachTicTacToe()
        }
    }

    internal inner class TicTacToeListener : TicTacToeInteractor.Listener {
        override fun gameWon(@Nullable winner: String?) {
            if (winner != null) {
                scoreStream.addVictory(winner)
            }
            router.detachTicTacToe()
            router.attachOffGame()
        }
    }
}