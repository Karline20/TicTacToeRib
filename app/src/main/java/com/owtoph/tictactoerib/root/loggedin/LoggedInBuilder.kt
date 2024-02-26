package com.owtoph.tictactoerib.root.loggedin

import com.owtoph.tictactoerib.root.RootBuilder
import com.owtoph.tictactoerib.root.RootView
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameBuilder
import com.owtoph.tictactoerib.root.loggedin.offgame.OffGameInteractor
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeBuilder
import com.owtoph.tictactoerib.root.loggedin.tictactoe.TicTacToeInteractor
import com.owtoph.tictactoerib.root.loggedout.LoggedOutBuilder
import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import dagger.Binds
import dagger.BindsInstance
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

    fun build(playerOne: String, playerTwo: String): LoggedInRouter {
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
        abstract fun scoreStream(@LoggedInInternal mutableScoreStream: MutableScoreStream): ScoreStream

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
                @Named("player_one") playerOne: String, @Named("player_two") playerTwo: String
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
            fun ticTacToeListener(interactor: LoggedInInteractor): TicTacToeInteractor.Listener {
                return interactor.TicTacToeListener()
            }
        }
    }

    @LoggedInScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<LoggedInInteractor>, BuilderComponent,
        OffGameBuilder.ParentComponent, TicTacToeBuilder.ParentComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: LoggedInInteractor): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component

            @BindsInstance
            fun playerOne(@Named("player_one") playerOne: String): Builder

            @BindsInstance
            fun playerTwo(@Named("player_two") playerTwo: String): Builder
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