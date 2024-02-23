package com.owtoph.tictactoerib.root

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor

import com.uber.rib.core.RibInteractor
import io.reactivex.annotations.Nullable
import javax.inject.Inject

/**
 * Created by Karlen Legaspi
 */

@RibInteractor
class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>() {

    @Inject lateinit var presenter: RootPresenter

    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        // Add attachment logic here (RxSubscriptions, etc.).
    }

    /** Presenter interface implemented by this RIB's view.  */
    interface RootPresenter
}