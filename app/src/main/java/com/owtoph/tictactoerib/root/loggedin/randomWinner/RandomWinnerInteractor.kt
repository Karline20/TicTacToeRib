package com.owtoph.tictactoerib.root.loggedin.randomWinner

import com.owtoph.tictactoerib.root.UserName
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.annotations.Nullable
import java.util.Random
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by Karlen Legaspi
 */

@RibInteractor
class RandomWinnerInteractor : Interactor<RandomWinnerInteractor.RandomWinnerPresenter, RandomWinnerRouter>() {

    @Inject lateinit var listener: Listener

    @Inject
    @Named("player_one") lateinit var playerOne: UserName

    @Inject
    @Named("player_two") lateinit var playerTwo: UserName
    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        if (Random(System.currentTimeMillis()).nextBoolean()) {
            listener.gameWon(playerOne)
        } else {
            listener.gameWon(playerTwo)
        }
    }

    /** Presenter interface implemented by this RIB's view.  */
    interface RandomWinnerPresenter
    interface Listener {
        /**
         * Called when the game is over.
         *
         * @param winner player that won, or null if it's a tie.
         */
        fun gameWon(winner: UserName)
    }
}