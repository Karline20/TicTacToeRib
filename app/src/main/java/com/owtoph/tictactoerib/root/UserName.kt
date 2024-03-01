package com.owtoph.tictactoerib.root

import android.support.annotation.NonNull
import com.google.auto.value.AutoValue

/**
 * Created by Karlen Legaspi
 */

@AutoValue
abstract class UserName {
    abstract fun getUserName(): String

    companion object {
        @NonNull
        fun create(userName: String): UserName {
            return AutoValue_UserName(userName)
        }
    }
}