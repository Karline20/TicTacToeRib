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
        //router.attachLoggedOut()
    }


    internal inner class LoggedOutListener : LoggedOutInteractor.Listener {
        fun requestLogin() {
            // Switch to logged in. Let’s just ignore userName for now.
            router.detachLoggedOut()
            router.attachLoggedIn()
        }
    }


    /** Presenter interface implemented by this RIB's view.  */
    interface RootPresenter{

    }
}