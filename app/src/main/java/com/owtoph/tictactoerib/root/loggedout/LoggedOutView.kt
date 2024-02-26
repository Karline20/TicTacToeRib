package com.owtoph.tictactoerib.root.loggedout

import android.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.RxView
import com.owtoph.tictactoerib.databinding.LoggedOutRibBinding
import io.reactivex.Observable
import io.reactivex.annotations.Nullable


/**
 * Created by Karlen Legaspi
 */
/** Top level view for [LoggedOutBuilder.LoggedOutScope].  */
class LoggedOutView @JvmOverloads constructor(context: Context?, @Nullable attrs: AttributeSet? = null, defStyle: Int = 0) :
    LinearLayout(context, attrs, defStyle), LoggedOutInteractor.LoggedOutPresenter {

        private lateinit var binding: LoggedOutRibBinding
        override fun onFinishInflate() {
            super.onFinishInflate()

            binding = LoggedOutRibBinding.bind(this)

        }


    override fun playerNames(): Observable<Pair<String, String>> {
        return RxView.clicks(binding.loginButton)
            .map {
                Pair(
                    binding.playerOneName.text.toString(),
                    binding.playerTwoName.text.toString()
                )
            }
    }
}