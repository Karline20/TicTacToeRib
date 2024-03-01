package com.owtoph.tictactoerib.root.loggedin

import com.google.common.collect.ImmutableMap
import com.owtoph.tictactoerib.root.UserName
import io.reactivex.Observable

/**
 * Created by Karlen Legaspi
 */

interface ScoreStream {
    fun scores(): Observable<ImmutableMap<UserName, Int>>

}