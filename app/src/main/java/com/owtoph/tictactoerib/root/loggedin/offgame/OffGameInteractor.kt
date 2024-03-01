package com.owtoph.tictactoerib.root.loggedin.offgame

import com.google.common.collect.ImmutableMap
import com.owtoph.tictactoerib.root.UserName
import com.owtoph.tictactoerib.root.loggedin.GameKey
import com.owtoph.tictactoerib.root.loggedin.ScoreStream
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
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
    lateinit var playerOne: UserName

    @Inject
    @Named("player_two")
    lateinit var playerTwo: UserName

    @Inject lateinit var listener: Listener

    @Inject lateinit var presenter: OffGamePresenter

    @Inject lateinit var scoreStream: ScoreStream

    @Inject lateinit var gameNames: List<GameKey>

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun didBecomeActive(@Nullable savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter
            .startGameRequest(gameNames)
            .subscribe{ gameKey ->
                listener.onStartGame(gameKey)
            }.let {
                if (it!=null) compositeDisposable.add(it)
            }


        scoreStream
            .scores()
            .`as`<ObservableSubscribeProxy<ImmutableMap<UserName, Int>>>(
                AutoDispose.autoDisposable<ImmutableMap<UserName, Int>>(
                    this
                )
            )
            .subscribe { scores ->
                val playerOneScore = scores[playerOne]
                val playerTwoScore = scores[playerTwo]
                presenter.setScores(playerOneScore!!, playerTwoScore!!)
            }
    }

    interface Listener {
        fun onStartGame(gameKey: GameKey)
    }

    /** Presenter interface implemented by this RIB's view.  */
    interface OffGamePresenter {
        fun setPlayerNames(playerOne: UserName, playerTwo: UserName)

        fun setScores(playerOneScore: Int, playerTwoScore: Int)

        fun startGameRequest(gameKeys: List<GameKey>): Observable<GameKey>
    }
}