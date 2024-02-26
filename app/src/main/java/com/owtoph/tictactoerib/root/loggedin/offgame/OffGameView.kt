package com.owtoph.tictactoerib.root.loggedin.offgame

import android.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.owtoph.tictactoerib.databinding.OffGameRibBinding
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameInteractor.OffGamePresenter
import com.uber.rib.core.Initializer
import io.reactivex.Observable
import java.util.Locale


/**
 * Created by Karlen Legaspi
 */
/** Top level view for [OffGameBuilder.OffGameScope].  */

/** Top level view for [OffGameBuilder.OffGameScope].  */
class OffGameView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) :
    LinearLayout(context, attrs, defStyle), OffGamePresenter {

        private lateinit var binding: OffGameRibBinding

    @Initializer
    override fun onFinishInflate() {
        super.onFinishInflate()

        binding = OffGameRibBinding.bind(this)
    }

    override fun setPlayerNames(playerOne: String, playerTwo: String) {
        binding.playerOneName.text = playerOne
        binding.playerTwoName.text = playerTwo
    }

    override fun setScores(playerOneScore: Int, playerTwoScore: Int) {
        this.binding.playerOneWinCount.text =
            String.format(Locale.getDefault(), "Win Count: %d", playerOneScore)
        this.binding.playerTwoWinCount.text =
            String.format(Locale.getDefault(), "Win Count: %d", playerTwoScore)
    }

    override fun startGameRequest(): Observable<Any> {
        return RxView.clicks(binding.startGameButton)
    }
}