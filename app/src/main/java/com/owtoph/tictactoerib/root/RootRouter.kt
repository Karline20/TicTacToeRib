package com.owtoph.tictactoerib.root

import com.google.common.base.Optional
import com.jakewharton.rxrelay2.BehaviorRelay
import com.owtoph.tictactoerib.root.loggedin.LoggedInActionableItem
import com.owtoph.tictactoerib.root.loggedin.LoggedInBuilder
import com.owtoph.tictactoerib.root.loggedout.LoggedOutBuilder
import com.owtoph.tictactoerib.root.loggedout.LoggedOutRouter
import com.uber.rib.core.ViewRouter


/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [RootBuilder.RootScope].  */
/** Adds and removes children of [RootBuilder.RootScope].  */
/** Adds and removes children of [RootBuilder.RootScope].  */
class RootRouter internal constructor(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    private val loggedOutBuilder: LoggedOutBuilder,
    private val loggedInBuilder: LoggedInBuilder
) : ViewRouter<RootView, RootInteractor>(
    view, interactor,
    component
) {
    private var loggedOutRouter: LoggedOutRouter? = null
    private val loggedInActionableItemRelay: BehaviorRelay<Optional<LoggedInActionableItem>> =
        BehaviorRelay.create<Optional<LoggedInActionableItem>>()

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

    fun attachLoggedIn(playerOne: UserName, playerTwo: UserName): LoggedInActionableItem {
        // No need to attach views in any way.
        val loggedInRouter = loggedInBuilder.build(playerOne, playerTwo)
        attachChild(loggedInRouter)
        loggedInActionableItemRelay.accept(
            Optional.of(loggedInRouter.interactor)
        )
        return loggedInRouter.interactor
    }
}