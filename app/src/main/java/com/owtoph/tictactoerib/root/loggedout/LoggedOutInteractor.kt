package com.owtoph.tictactoerib.root.loggedout

import android.util.Log
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [LoggedOutScope].  */
@RibInteractor
class LoggedOutInteractor : Interactor<LoggedOutInteractor.LoggedOutPresenter, LoggedOutRouter>() {

    @Inject lateinit var listener: Listener
    @Inject lateinit var presenter: LoggedOutPresenter

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }
    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter
            .playerNames()
            .subscribe { names ->
                names.let { pair ->
                    if (!isEmpty(pair.first) && !isEmpty(pair.second)) {
                        listener.requestLogin(pair.first, pair.second)
                    }
                }
            }.let {
                compositeDisposable.add(
                    it
                )
            }

    }

    private fun isEmpty(string: String): Boolean {
        return string == null || string.length == 0
    }

    /** Presenter interface implemented by this RIB's view.  */
    interface LoggedOutPresenter {
        fun playerNames(): Observable<Pair<String, String>>
    }


    interface Listener {
        fun requestLogin(playerOne: String, playerTwo: String)
    }

}