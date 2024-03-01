package com.owtoph.tictactoerib.root.loggedin

import android.view.ViewGroup
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameBuilder
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameRouter
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeBuilder
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter


/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [LoggedInBuilder.LoggedInScope].  */
/** Adds and removes children of [LoggedInBuilder.LoggedInScope].  */
class LoggedInRouter internal constructor(
    interactor: LoggedInInteractor,
    component: LoggedInBuilder.Component?,
    private val parentView: ViewGroup,
    private val offGameBuilder: OffGameBuilder,
    private val ticTacToeBuilder: TicTacToeBuilder
) : Router<LoggedInInteractor>(interactor, component) {
    private var offGameRouter: OffGameRouter? = null
    private var gameRouter: ViewRouter<*, *>? = null
    override fun willDetach() {
        super.willDetach()
        detachOffGame()
        detachGame()
    }

    fun attachOffGame() {
        offGameRouter = offGameBuilder.build(parentView)
        attachChild(offGameRouter!!)
        parentView.addView(offGameRouter!!.view)
    }

    fun detachOffGame() {
        if (offGameRouter != null) {
            detachChild(offGameRouter!!)
            parentView.removeView(offGameRouter!!.view)
            offGameRouter = null
        }
    }

    fun attachGame(gameProvider: GameProvider) {
        gameRouter = gameProvider.viewRouter(parentView)
        parentView.addView(gameRouter!!.view)
        attachChild(gameRouter!!)
    }

    fun detachGame() {
        if (gameRouter != null) {
            detachChild(gameRouter!!)
            parentView.removeView(gameRouter!!.view)
            gameRouter = null
        }
    }
}