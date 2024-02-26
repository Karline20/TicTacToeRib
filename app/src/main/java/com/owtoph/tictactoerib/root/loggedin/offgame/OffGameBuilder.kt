package com.owtoph.tictactoerib.root.loggedin.offgame

import android.view.LayoutInflater
import android.view.ViewGroup
import com.owtoph.tictactoerib.R
import com.owtoph.tictactoerib.root.loggedin.ScoreStream
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope


/**
 * Created by Karlen Legaspi
 */

class OffGameBuilder(dependency: ParentComponent) :
    ViewBuilder<OffGameView, OffGameRouter, OffGameBuilder.ParentComponent>(dependency) {
    /**
     * Builds a new [OffGameRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [OffGameRouter].
     */
    fun build(parentViewGroup: ViewGroup): OffGameRouter {
        val view = createView(parentViewGroup)
        val interactor = OffGameInteractor()
        val component: Component = DaggerOffGameBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.offgameRouter()
    }

    protected override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): OffGameView {
        return inflater.inflate(R.layout.off_game_rib, parentViewGroup, false) as OffGameView
    }

    interface ParentComponent {
        fun listener(): OffGameInteractor.Listener

        @Named("player_one")
        fun playerOne(): String

        @Named("player_two")
        fun playerTwo(): String

        fun scoreStream(): ScoreStream
    }

    @dagger.Module
    abstract class Module {
        @OffGameScope
        @Binds
        abstract fun presenter(view: OffGameView): OffGameInteractor.OffGamePresenter

        companion object {
            @OffGameScope
            @Provides
            fun router(
                component: Component, view: OffGameView, interactor: OffGameInteractor
            ): OffGameRouter {
                return OffGameRouter(view, interactor, component)
            }
        }
    }

    @OffGameScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<OffGameInteractor>, BuilderComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: OffGameInteractor): Builder

            @BindsInstance
            fun view(view: OffGameView): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun offgameRouter(): OffGameRouter
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class OffGameScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class OffGameInternal
}