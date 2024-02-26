package com.owtoph.tictactoerib.root

import com.owtoph.tictactoerib.root.loggedin.LoggedInBuilder
import com.owtoph.tictactoerib.root.loggedout.LoggedOutBuilder
import com.owtoph.tictactoerib.root.loggedout.LoggedOutRouter
import com.uber.rib.core.ViewRouter
import io.reactivex.annotations.Nullable


/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [RootBuilder.RootScope].  */
/** Adds and removes children of [RootBuilder.RootScope].  */
class RootRouter internal constructor(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    private val loggedOutBuilder: LoggedOutBuilder,
    loggedInBuilder: LoggedInBuilder
) : ViewRouter<RootView, RootInteractor>(
    view, interactor,
    component
) {
    private val loggedInBuilder: LoggedInBuilder

    @Nullable
    private var loggedOutRouter: LoggedOutRouter? = null

    init {
        this.loggedInBuilder = loggedInBuilder
    }

    fun attachLoggedOut() {
        loggedOutRouter = loggedOutBuilder.build(view)
        attachChild(loggedOutRouter!!)
        view.addView(loggedOutRouter!!.view)
    }

    fun detachLoggedOut() {
        if (loggedOutRouter != null) {
            detachChild(loggedOutRouter!!)
            view.removeView(loggedOutRouter!!.view)
            loggedOutRouter = null
        }
    }

    fun attachLoggedIn(playerOne: String, playerTwo: String) {
        attachChild(loggedInBuilder.build(playerOne, playerTwo))
    }
}