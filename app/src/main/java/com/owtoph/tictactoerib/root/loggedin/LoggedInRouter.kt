package com.owtoph.tictactoerib.root.loggedin

import android.view.ViewGroup
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameBuilder
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameRouter
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeBuilder
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeRouter
import com.uber.rib.core.Router


/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [LoggedInBuilder.LoggedInScope].  */
class LoggedInRouter internal constructor(
    interactor: LoggedInInteractor,
    component: LoggedInBuilder.Component,
    private val parentView: ViewGroup,
    private val offGameBuilder: OffGameBuilder,
    private val ticTacToeBuilder: TicTacToeBuilder
) : Router<LoggedInInteractor>(interactor, component) {
    private var offGameRouter: OffGameRouter? = null
    private var ticTacToeRouter: TicTacToeRouter? = null
    override fun willDetach() {
        super.willDetach()
        detachOffGame()
        detachTicTacToe()
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

    fun attachTicTacToe() {
        ticTacToeRouter = ticTacToeBuilder.build(parentView)
        attachChild(ticTacToeRouter!!)
        parentView.addView(ticTacToeRouter!!.view)
    }

    fun detachTicTacToe() {
        if (ticTacToeRouter != null) {
            detachChild(ticTacToeRouter!!)
            parentView.removeView(ticTacToeRouter!!.view)
            ticTacToeRouter = null
        }
    }
}