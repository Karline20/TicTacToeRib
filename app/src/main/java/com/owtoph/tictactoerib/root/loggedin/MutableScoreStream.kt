package com.owtoph.tictactoerib.root.loggedin

import com.google.common.collect.ImmutableMap

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable


/**
 * Created by Karlen Legaspi
 */

class MutableScoreStream(playerOne: String, playerTwo: String) : ScoreStream {
    private val scoresRelay = BehaviorRelay.create<ImmutableMap<String, Int>>()

    init {
        scoresRelay.accept(ImmutableMap.of(playerOne, 0, playerTwo, 0))
    }

    fun addVictory(userName: String) {
        val currentScores = scoresRelay.value!!
        val newScoreMapBuilder = ImmutableMap.Builder<String, Int>()
        for ((key, value) in currentScores) {
            if (key == userName) {
                newScoreMapBuilder.put(key, value + 1)
            } else {
                newScoreMapBuilder.put(key, value)
            }
        }
        scoresRelay.accept(newScoreMapBuilder.build())
    }

    override fun scores(): Observable<ImmutableMap<String, Int>> {
        return scoresRelay.hide()
    }
}