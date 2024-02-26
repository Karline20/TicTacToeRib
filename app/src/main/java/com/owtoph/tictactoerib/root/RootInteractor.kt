package com.owtoph.tictactoerib.root

import com.owtoph.tictactoerib.root.loggedout.LoggedOutInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject


/**
 * Created by Karlen Legaspi
 */

@RibInteractor
class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>(), RootActionableItem  {

    @Inject lateinit var presenter: RootPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachLoggedOut()
    }
    inner class LoggedOutListener : LoggedOutInteractor.Listener {
        override fun requestLogin(playerOne: String, playerTwo: String) {
            // Switch to logged in. Let’s just ignore userName for now.

            router.detachLoggedOut()
            router.attachLoggedIn(playerOne, playerTwo)

        }

    }



    /** Presenter interface implemented by this RIB's view.  */
    interface RootPresenter
}