package com.owtoph.tictactoerib.root.loggedout

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [LoggedOutScope].  */

@RibInteractor
class LoggedOutInteractor : Interactor<LoggedOutInteractor.LoggedOutPresenter, LoggedOutRouter>() {

    @Inject lateinit var listener: Listener

    @Inject lateinit var presenter: LoggedOutPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

    }

    /** Presenter interface implemented by this RIB's view.  */
    interface LoggedOutPresenter {

    }

    interface Listener {

    }
}