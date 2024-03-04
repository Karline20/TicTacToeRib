package com.owtoph.tictactoerib.root.loggedin

import com.owtoph.tictactoerib.root.RootView
import com.uber.rib.core.Router


/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [LoggedInBuilder.LoggedInScope].  */
/** Adds and removes children of [LoggedInBuilder.LoggedInScope].  */
class LoggedInRouter (interactor: LoggedInInteractor, component: LoggedInBuilder.Component, rootView: RootView
) : Router<LoggedInInteractor>(interactor, component) {

}