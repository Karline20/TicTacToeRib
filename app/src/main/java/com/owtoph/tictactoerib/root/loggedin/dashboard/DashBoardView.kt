package com.owtoph.tictactoerib.root.loggedin.dashboard

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.owtoph.tictactoerib.databinding.DashBoardRibBinding
import com.uber.rib.core.Initializer
import io.reactivex.annotations.Nullable


/**
 * Created by Karlen Legaspi
 */
/** Top level view for [DashBoardBuilder.DashBoardScope].  */
class DashBoardView @JvmOverloads constructor(context: Context?, @Nullable attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    LinearLayout(context, attrs, defStyle), DashBoardInteractor.DashBoardPresenter {

        lateinit var binding: DashBoardRibBinding

        override fun onFinishInflate() {
            super.onFinishInflate()
            binding = DashBoardRibBinding.bind(this)

        }

}