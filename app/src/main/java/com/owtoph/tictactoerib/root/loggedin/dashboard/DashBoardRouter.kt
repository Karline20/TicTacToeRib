package com.owtoph.tictactoerib.root.loggedin.dashboard

import com.uber.rib.core.ViewRouter




/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [DashBoardBuilder.DashBoardScope].  */
class DashBoardRouter(view: DashBoardView, interactor: DashBoardInteractor, component: DashBoardBuilder.Component) :
    ViewRouter<DashBoardView, DashBoardInteractor>(view, interactor, component)