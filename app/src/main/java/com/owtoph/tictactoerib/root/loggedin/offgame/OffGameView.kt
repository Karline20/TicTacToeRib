package com.owtoph.tictactoerib.root.loggedin.offgame

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.RxView
import com.owtoph.tictactoerib.R
import com.owtoph.tictactoerib.databinding.OffGameRibBinding
import com.owtoph.tictactoerib.root.UserName
import com.owtoph.tictactoerib.root.loggedin.GameKey
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

    override fun setPlayerNames(playerOne: UserName, playerTwo: UserName) {
        binding.playerOneName.text = playerOne.getUserName()
        binding.playerTwoName.text = playerTwo.getUserName()
    }

    override fun setScores(playerOneScore: Int, playerTwoScore: Int) {
        this.binding.playerOneWinCount.text =
            String.format(Locale.getDefault(), "Win Count: %d", playerOneScore)
        this.binding.playerTwoWinCount.text =
            String.format(Locale.getDefault(), "Win Count: %d", playerTwoScore)
    }

    override fun startGameRequest(gameKeys: List<GameKey>): Observable<GameKey> {
        val observables: MutableList<Observable<GameKey>> = ArrayList<Observable<GameKey>>()
        for (gameKey in gameKeys) {
            val button = LayoutInflater.from(context).inflate(R.layout.game_button, this, false) as Button
            button.text = gameKey.gameName()
            val observable: Observable<GameKey> = RxView.clicks(button).map { gameKey }
            observables.add(observable)
            addView(button)
        }
        return Observable.merge(observables)
    }
}