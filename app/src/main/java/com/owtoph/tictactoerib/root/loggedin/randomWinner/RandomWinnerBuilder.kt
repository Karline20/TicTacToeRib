package com.owtoph.tictactoerib.root.loggedin.randomWinner

import android.view.LayoutInflater
import android.view.ViewGroup
import com.owtoph.tictactoerib.root.UserName
import com.owtoph.tictactoerib.root.loggedin.randomWinner.RandomWinnerBuilder.RandomWinnerScope
import com.owtoph.tictactoerib.root.loggedin.randomWinner.RandomWinnerInteractor.RandomWinnerPresenter
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
/** Builder for the [OffGameScope].  */
/**
 * Builder for the [RandomWinnerScope]. Not a real game. This just picks a random winner than
 * exits.
 */

class RandomWinnerBuilder(dependency: ParentComponent) :
    ViewBuilder<RandomWinnerView, RandomWinnerRouter, RandomWinnerBuilder.ParentComponent>(
        dependency
    ) {
    /**
     * Builds a new [RandomWinnerRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RandomWinnerRouter].
     */
    fun build(parentViewGroup: ViewGroup): RandomWinnerRouter {
        val view = createView(parentViewGroup)
        val interactor = RandomWinnerInteractor()
        val component = DaggerRandomWinnerBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.randomwinnerRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): RandomWinnerView {
        // Just inflate a silly useless view that does nothing.
        return RandomWinnerView(parentViewGroup.context)
    }

    interface ParentComponent {
        fun randomWinnerListener(): RandomWinnerInteractor.Listener

        @Named("player_one")
        fun playerOne(): UserName

        @Named("player_two")
        fun playerTwo(): UserName
    }

    @dagger.Module
    abstract class Module {
        @RandomWinnerScope
        @Binds
        abstract fun presenter(view: RandomWinnerView): RandomWinnerPresenter

        companion object {
            @RandomWinnerScope
            @Provides
            fun router(
                component: Component, view: RandomWinnerView, interactor: RandomWinnerInteractor
            ): RandomWinnerRouter {
                return RandomWinnerRouter(view, interactor, component)
            }
        }
    }

    @RandomWinnerScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<RandomWinnerInteractor>,
        BuilderComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: RandomWinnerInteractor): Builder

            @BindsInstance
            fun view(view: RandomWinnerView): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun randomwinnerRouter(): RandomWinnerRouter
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class RandomWinnerScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class RandomWinnerInternal
}
