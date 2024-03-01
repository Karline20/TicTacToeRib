package com.owtoph.tictactoerib.root.loggedin

import android.view.ViewGroup
import com.google.common.collect.Lists
import com.owtoph.tictactoerib.root.RootView
import com.owtoph.tictactoerib.root.UserName
import com.owtoph.tictactoerib.root.loggedin.LoggedInInteractor.GameListener
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameBuilder
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameInteractor
import com.owtoph.tictactoerib.root.loggedin.randomWinner.RandomWinnerBuilder
import com.owtoph.tictactoerib.root.loggedin.randomWinner.RandomWinnerInteractor
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeBuilder
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeInteractor
import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewRouter
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope


/**
 * Created by Karlen Legaspi
 */
class LoggedInBuilder(dependency: ParentComponent) :
    Builder<LoggedInRouter, LoggedInBuilder.ParentComponent>(dependency) {
    /**
     * Builds a new [LoggedInRouter].
     *
     * @return a new [LoggedInRouter].
     */
    fun build(playerOne: UserName, playerTwo: UserName): LoggedInRouter {
        val interactor = LoggedInInteractor()
        val component: Component = DaggerLoggedInBuilder_Component.builder()
            .parentComponent(dependency)
            .interactor(interactor)
            .playerOne(playerOne)
            .playerTwo(playerTwo)
            .build()
        return component.loggedinRouter()
    }

    interface ParentComponent {
        fun rootView(): RootView
    }

    @dagger.Module
    abstract class Module {
        @LoggedInScope
        @Binds
        abstract fun randomWinnerListener(
            listener: GameListener
        ): RandomWinnerInteractor.Listener

        @LoggedInScope
        @Binds
        abstract fun ticTacToeGameListener(
            listener: GameListener
        ): TicTacToeInteractor.Listener

        @LoggedInScope
        @Binds
        abstract fun scoreStream(@LoggedInInternal mutableScoreStream: MutableScoreStream): ScoreStream
        @LoggedInScope
        @Binds
        abstract fun gameKeys(@LoggedInInternal gameProviders: List<GameProvider>): List<GameKey>

        companion object {
            @LoggedInScope
            @Provides
            fun presenter(): EmptyPresenter {
                return EmptyPresenter()
            }

            @LoggedInScope
            @Provides
            fun router(
                component: Component, interactor: LoggedInInteractor, rootView: RootView
            ): LoggedInRouter {
                return LoggedInRouter(
                    interactor,
                    component,
                    rootView,
                    OffGameBuilder(component),
                    TicTacToeBuilder(component)
                )
            }

            @LoggedInScope
            @LoggedInInternal
            @Provides
            fun mutableScoreStream(
                @Named("player_one") playerOne: UserName, @Named("player_two") playerTwo: UserName
            ): MutableScoreStream {
                return MutableScoreStream(playerOne, playerTwo)
            }

            @LoggedInScope
            @Provides
            fun listener(interactor: LoggedInInteractor): OffGameInteractor.Listener {
                return interactor.OffGameListener()
            }

            @LoggedInScope
            @Provides
            fun ticTacToeListener(interactor: LoggedInInteractor): GameListener {
                return interactor.GameListener()
            }

            @LoggedInInternal
            @Provides
            fun gameProviders(component: Component): List<GameProvider> {
                // Decorate the game builders with a "name" key so we can treat them generically elsewhere.
                val ticTacToeGame: GameProvider = object : GameProvider {
                    override fun gameName(): String {
                        return "TicTacToe"
                    }

                    override fun viewRouter(viewGroup: ViewGroup): ViewRouter<*, *> {
                        return TicTacToeBuilder(component).build(viewGroup)
                    }
                }
                val randomWinnerGame: GameProvider = object : GameProvider {
                    override fun gameName(): String {
                        return "RandomWinner"
                    }

                    override fun viewRouter(viewGroup: ViewGroup): ViewRouter<*, *> {
                        return RandomWinnerBuilder(component).build(viewGroup)
                    }
                }
                return Lists.newArrayList(ticTacToeGame, randomWinnerGame)
            }
        }
    }

    @LoggedInScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<LoggedInInteractor>, BuilderComponent,
        OffGameBuilder.ParentComponent,
        TicTacToeBuilder.ParentComponent,
        RandomWinnerBuilder.ParentComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: LoggedInInteractor): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
            @BindsInstance
            fun playerOne(@Named("player_one") playerOne: UserName): Builder
            @BindsInstance
            fun playerTwo(@Named("player_two") playerTwo: UserName): Builder
        }
    }

    interface BuilderComponent {
        fun loggedinRouter(): LoggedInRouter
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class LoggedInScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class LoggedInInternal
}