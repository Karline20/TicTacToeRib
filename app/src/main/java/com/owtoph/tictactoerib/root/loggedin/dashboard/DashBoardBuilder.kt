package com.owtoph.tictactoerib.root.loggedin.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import com.owtoph.tictactoerib.R
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Created by Karlen Legaspi
 */

class DashBoardBuilder(dependency: ParentComponent) :
    ViewBuilder<DashBoardView, DashBoardRouter, DashBoardBuilder.ParentComponent>(dependency) {
    /**
     * Builds a new [DashBoardRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [DashBoardRouter].
     */
    fun build(parentViewGroup: ViewGroup): DashBoardRouter {
        val view: DashBoardView = createView(parentViewGroup)
        val interactor = DashBoardInteractor()
        val component: Component = DaggerDashBoardBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.DashBoardRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): DashBoardView {
        return inflater.inflate(R.layout.dash_board_rib, parentViewGroup, false) as DashBoardView
    }

    interface ParentComponent {
        fun listener(): DashBoardInteractor.Listener

    }

    @dagger.Module
    abstract class Module {
        @DashBoardScope
        @Binds
        abstract fun presenter(view: DashBoardView): DashBoardInteractor.DashBoardPresenter

        companion object {
            @DashBoardScope
            @Provides
            fun router(
                component: Component, view: DashBoardView, interactor: DashBoardInteractor
            ): DashBoardRouter {
                return DashBoardRouter(view, interactor, component)
            }
        }
    }

    @DashBoardScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<DashBoardInteractor>, BuilderComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: DashBoardInteractor): Builder

            @BindsInstance
            fun view(view: DashBoardView): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun DashBoardRouter(): DashBoardRouter
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class DashBoardScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class DashBoardInternal
}