package com.owtoph.tictactoerib.root.loggedout

import android.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.annotations.Nullable


/**
 * Created by Karlen Legaspi
 */
/** Top level view for [LoggedOutBuilder.LoggedOutScope].  */
class LoggedOutView @JvmOverloads constructor(
    context: Context?,
    @Nullable attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    LinearLayout(context, attrs, defStyle), LoggedOutInteractor.LoggedOutPresenter {

    override fun loginName(): Observable<String> {

        return RxView.clicks(findViewById(R.id.login_button))
            .map(
                object : Function<Any?, String?>() {
                    @Throws(Exception::class)
                    fun apply(o: Any?): String {
                        val textView = findViewById<View>(R.id.edit_text) as TextView
                        return textView.text.toString()
                    }
                })
    }
}