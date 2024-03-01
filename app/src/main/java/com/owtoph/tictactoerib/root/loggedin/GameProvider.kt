package com.owtoph.tictactoerib.root.loggedin

import android.view.ViewGroup
import com.uber.rib.core.ViewRouter


/**
 * Created by Karlen Legaspi
 */


interface GameProvider : GameKey {
    override fun gameName(): String
    fun viewRouter(viewGroup: ViewGroup): ViewRouter<*, *>
}