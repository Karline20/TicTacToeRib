package com.owtoph.tictactoerib.root.loggedin

import com.google.common.collect.ImmutableMap
import io.reactivex.Observable

/**
 * Created by Karlen Legaspi
 */

open interface ScoreStream {
    fun scores(): Observable<ImmutableMap<String, Int>>

}