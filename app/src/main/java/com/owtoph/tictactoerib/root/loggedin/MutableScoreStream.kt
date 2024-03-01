package com.owtoph.tictactoerib.root.loggedin

import com.google.common.collect.ImmutableMap
import com.jakewharton.rxrelay2.BehaviorRelay
import com.owtoph.tictactoerib.root.UserName
import io.reactivex.Observable


/**
 * Created by Karlen Legaspi
 */


class MutableScoreStream(playerOne: UserName, playerTwo: UserName) : ScoreStream {
    private val scoresRelay = BehaviorRelay.create<ImmutableMap<UserName, Int>>()

    init {
        scoresRelay.accept(ImmutableMap.of(playerOne, 0, playerTwo, 0))
    }

    fun addVictory(userName: UserName) {
        val currentScores = scoresRelay.value!!
        val newScoreMapBuilder = ImmutableMap.Builder<UserName, Int>()
        for ((key, value) in currentScores) {
            if (key == userName) {
                newScoreMapBuilder.put(key, value + 1)
            } else {
                newScoreMapBuilder.put(key, value)
            }
        }
        scoresRelay.accept(newScoreMapBuilder.build())
    }

    override fun scores(): Observable<ImmutableMap<UserName, Int>> {
        return scoresRelay.hide()
    }
}