package com.owtoph.tictactoerib.root.loggedin.tictactoe

/**
 * Created by Karlen Legaspi
 */

class BoardCoordinate(x: Int, y: Int) {
    private val x: Int
    private val y: Int

    init {
        this.x = x
        this.y = y
    }

    fun getX(): Int {
        return x
    }

    fun getY(): Int {
        return y
    }
}