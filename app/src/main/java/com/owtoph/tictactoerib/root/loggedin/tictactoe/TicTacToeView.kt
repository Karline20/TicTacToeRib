package com.owtoph.tictactoerib.root.loggedin.tictactoe

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.percentlayout.widget.PercentRelativeLayout
import com.jakewharton.rxbinding2.view.RxView
import com.owtoph.tictactoerib.databinding.TicTacToeRibBinding
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeInteractor.TicTacToePresenter
import com.uber.rib.core.Initializer
import io.reactivex.Observable
import io.reactivex.annotations.Nullable


/**
 * Created by Karlen Legaspi
 */
/** Top level view for [TicTacToeBuilder.TicTacToeScope].  */
class TicTacToeView @JvmOverloads constructor(context: Context?, @Nullable attrs: AttributeSet? = null, defStyle: Int = 0) :
    PercentRelativeLayout(context, attrs, defStyle), TicTacToePresenter {

        private lateinit var binding: TicTacToeRibBinding
    private lateinit var imageButtons: Array<Array<TextView>?>
    private var titleView: TextView? = null

    @Initializer
    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = TicTacToeRibBinding.bind(this)

        imageButtons = arrayOfNulls<Array<TextView>>(3)

        imageButtons[0] = arrayOf(
            binding.button11,
            binding.button12,
            binding.button13
        )
        imageButtons[1] = arrayOf(
            binding.button21,
            binding.button22,
            binding.button23
        )
        imageButtons[2] = arrayOf(
            binding.button31,
            binding.button32,
            binding.button33
        )
        titleView = binding.title
    }

    override fun squareClicks(): Observable<BoardCoordinate> {
        val observables: ArrayList<Observable<BoardCoordinate>> = ArrayList()
        for (i in 0..2) {
            for (j in 0..2) {
                observables.add(
                    RxView.clicks(imageButtons[i]?.get(j) ?: imageButtons[i]!![j])
                        .map { BoardCoordinate(i, j) }
                )
            }
        }
        return Observable.merge(observables)
    }

    override fun addCross(xy: BoardCoordinate?) {
        val textView = imageButtons[xy!!.getX()]?.get(xy.getY())
        textView?.text = "x"
        textView?.isClickable = false
    }

    override fun addNought(xy: BoardCoordinate?) {
        val textView = imageButtons[xy!!.getX()]?.get(xy.getY())
        textView?.text = "O"
        textView?.isClickable = false
    }

    override fun setCurrentPlayerName(currentPlayer: String?) {
        titleView!!.text = "Current Player: $currentPlayer"
    }

    override fun setPlayerWon(playerName: String?) {
        titleView!!.text = "Player won: $playerName!!!"
    }

    override fun setPlayerTie() {
        titleView!!.text = "Tie game!"
    }
}