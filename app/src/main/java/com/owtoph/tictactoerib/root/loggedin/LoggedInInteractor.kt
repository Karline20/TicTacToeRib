package com.owtoph.tictactoerib.root.loggedin

import com.owtoph.tictactoerib.root.UserName
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameInteractor
import com.owtoph.tictactoerib.root.loggedin.randomWinner.RandomWinnerInteractor
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
class LoggedInInteractor : Interactor<EmptyPresenter, LoggedInRouter>(), LoggedInActionableItem {

    @Inject
    @LoggedInBuilder.LoggedInInternal
    lateinit var scoreStream: MutableScoreStream

    @Inject
    @LoggedInBuilder.LoggedInInternal
    lateinit var gameProviders: List<GameProvider>

    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        // when first logging in we should be in the OffGame state
        router.attachOffGame()
    }

    override fun willResignActive() {
        super.willResignActive()

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

    inner class OffGameListener : OffGameInteractor.Listener {
        override fun onStartGame(gameKey: GameKey) {
            router.detachOffGame()
            for (gameProvider in gameProviders) {
                if (gameProvider.gameName() == gameKey.gameName()) {
                    router.attachGame(gameProvider)
                }
            }
        }
    }


    inner class GameListener : TicTacToeInteractor.Listener,
        RandomWinnerInteractor.Listener {
        override fun gameWon(winner: UserName) {
            if (winner != null) {
                scoreStream.addVictory(winner)
            }
            router.detachGame()
            router.attachOffGame()
        }

    }


}