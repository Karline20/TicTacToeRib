package com.owtoph.tictactoerib.root.loggedout

import com.uber.rib.core.ViewRouter




/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [LoggedOutBuilder.LoggedOutScope].  */
class LoggedOutRouter(
    view: LoggedOutView, interactor: LoggedOutInteractor, component: LoggedOutBuilder.Component
) :
    ViewRouter<LoggedOutView, LoggedOutInteractor>(view, interactor, component)