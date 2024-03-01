package com.owtoph.tictactoerib.root.loggedin.tictactoe

import android.view.LayoutInflater
import android.view.ViewGroup
import com.owtoph.tictactoerib.R
import com.owtoph.tictactoerib.root.UserName
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
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

class TicTacToeBuilder(dependency: ParentComponent) :
    ViewBuilder<TicTacToeView, TicTacToeRouter, TicTacToeBuilder.ParentComponent>(dependency) {
    /**
     * Builds a new [TicTacToeRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [TicTacToeRouter].
     */
    fun build(parentViewGroup: ViewGroup): TicTacToeRouter {
        val view: TicTacToeView = createView(parentViewGroup)
        val interactor = TicTacToeInteractor()
        val component: Component = DaggerTicTacToeBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.tictactoeRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): TicTacToeView {
        return inflater.inflate(R.layout.tic_tac_toe_rib, parentViewGroup, false) as TicTacToeView
    }

    interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
        fun ticTacToeListener(): TicTacToeInteractor.Listener

        @Named("player_one")
        fun playerOne(): UserName

        @Named("player_two")
        fun playerTwo(): UserName
    }

    @dagger.Module
    abstract class Module {
        @TicTacToeScope
        @Binds
        abstract fun presenter(view: TicTacToeView): TicTacToeInteractor.TicTacToePresenter

        companion object {
            @TicTacToeScope
            @Provides
            fun router(
                component: Component, view: TicTacToeView, interactor: TicTacToeInteractor
            ): TicTacToeRouter {
                return TicTacToeRouter(view, interactor, component)
            }
        }
    }

    @TicTacToeScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<TicTacToeInteractor>,
        BuilderComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: TicTacToeInteractor): Builder

            @BindsInstance
            fun view(view: TicTacToeView): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun tictactoeRouter(): TicTacToeRouter
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class TicTacToeScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class TicTacToeInternal
}