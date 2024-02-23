package com.owtoph.tictactoerib.root.loggedout

import android.util.Log
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.annotations.Nullable
import io.reactivex.functions.Consumer
import javax.inject.Inject


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [LoggedOutScope].  */
@RibInteractor
class LoggedOutInteractor :
    Interactor<LoggedOutInteractor.LoggedOutPresenter, LoggedOutRouter>() {
    @Inject
    var presenter: LoggedOutPresenter? = null
    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter
            ?.loginName()
            ?.subscribe(
                object : Consumer<String> {
                    @Throws(Exception::class)
                    override fun accept(name: String) {
                        Log.d("MOO", name!!)
                    }
                })
    }

    /** Presenter interface implemented by this RIB's view.  */
    interface LoggedOutPresenter {
        fun loginName(): Observable<String>
    }
}