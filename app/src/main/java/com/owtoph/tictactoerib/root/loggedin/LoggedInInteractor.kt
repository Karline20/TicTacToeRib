package com.owtoph.tictactoerib.root.loggedin

import com.owtoph.tictactoerib.root.loggedin.dashboard.DashBoardInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [LoggedInScope].  */

@RibInteractor
class LoggedInInteractor : Interactor<EmptyPresenter, LoggedInRouter>() {

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        // when first logging in we should be in the OffGame state
    }

    override fun willResignActive() {
        super.willResignActive()

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }


    internal inner class DashBoardListener : DashBoardInteractor.Listener {

    }


}