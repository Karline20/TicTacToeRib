package com.owtoph.tictactoerib.root

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import io.reactivex.annotations.Nullable


/**
 * Created by Karlen Legaspi
 */

class RootView @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    FrameLayout(context, attrs, defStyle), RootInteractor.RootPresenter