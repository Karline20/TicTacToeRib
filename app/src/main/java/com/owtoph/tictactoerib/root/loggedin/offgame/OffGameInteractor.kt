package com.owtoph.tictactoerib.root.loggedin.offgame

import com.google.common.collect.ImmutableMap
import com.owtoph.tictactoerib.root.loggedin.ScoreStream
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by Karlen Legaspi
 */
/** Coordinates Business Logic for [OffGameScope].  */
@RibInteractor
class OffGameInteractor : Interactor<OffGameInteractor.OffGamePresenter, OffGameRouter>() {

    @Inject
    @Named("player_one")
    lateinit var playerOne: String

    @Inject
    @Named("player_two")
    lateinit var playerTwo: String

    @Inject lateinit var listener: Listener

    @Inject lateinit var presenter: OffGamePresenter

    @Inject lateinit var scoreStream: ScoreStream

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        compositeDisposable.add(
            presenter
                .startGameRequest()
                .subscribe {
                    listener.onStartGame()
                }
        )

        scoreStream
            .scores()
            .`as`<ObservableSubscribeProxy<ImmutableMap<String, Int>>>(
                AutoDispose.autoDisposable(
                    this
                )
            )
            .subscribe{it
                val playerOneScore = it[playerOne]
                val playerTwoScore = it[playerTwo]
                presenter.setScores(playerOneScore!!, playerTwoScore!!)

            }
    }

    interface Listener {
        fun onStartGame()
    }

    /** Presenter interface implemented by this RIB's view.  */
    interface OffGamePresenter {
        fun setPlayerNames(playerOne: String, playerTwo: String)

        fun setScores(playerOneScore: Int, playerTwoScore: Int)

        fun startGameRequest(): Observable<Any>
    }
}