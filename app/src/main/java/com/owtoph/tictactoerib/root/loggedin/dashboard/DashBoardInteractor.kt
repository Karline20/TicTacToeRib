package com.owtoph.tictactoerib.root.loggedin.dashboard

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [DashBoardScope].  */
@RibInteractor
class DashBoardInteractor : Interactor<DashBoardInteractor.DashBoardPresenter, DashBoardRouter>() {

    @Inject lateinit var listener: Listener

    @Inject lateinit var presenter: DashBoardPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

    }

    interface Listener {

    }

    /** Presenter interface implemented by this RIB's view.  */
    interface DashBoardPresenter {

    }
}