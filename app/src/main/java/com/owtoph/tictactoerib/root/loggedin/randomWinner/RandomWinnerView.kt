package com.owtoph.tictactoerib.root.loggedin.randomWinner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.owtoph.tictactoerib.root.loggedin.randomWinner.RandomWinnerInteractor.RandomWinnerPresenter
import io.reactivex.annotations.Nullable


/**
 * Created by Karlen Legaspi
 */
/** Top level view for [RandomWinnerBuilder.RandomWinnerScope].  */
class RandomWinnerView @JvmOverloads constructor(context: Context?, @Nullable attrs: AttributeSet? = null, defStyle: Int = 0) :
    FrameLayout(context!!, attrs, defStyle), RandomWinnerPresenter