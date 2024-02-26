package com.owtoph.tictactoerib.root

import android.view.LayoutInflater
import android.view.ViewGroup
import com.owtoph.tictactoerib.R
import com.owtoph.tictactoerib.root.RootBuilder.RootScope
import com.owtoph.tictactoerib.root.RootInteractor.RootPresenter
import com.owtoph.tictactoerib.root.loggedin.LoggedInBuilder
import com.owtoph.tictactoerib.root.loggedout.LoggedOutBuilder
import com.owtoph.tictactoerib.root.loggedout.LoggedOutInteractor
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Scope


/**
 * Created by Karlen Legaspi
 */
/** Builder for the [RootScope].  */
/** Builder for the [RootScope].  */
class RootBuilder(dependency: ParentComponent) :
    ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent>(dependency) {
    /**
     * Builds a new [RootRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RootRouter].
     */
    fun build(parentViewGroup: ViewGroup): RootRouter {
        val view = createView(parentViewGroup)
        val interactor = RootInteractor()
        val component: Component = DaggerRootBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.rootRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): RootView {
        return inflater.inflate(R.layout.root_rib, parentViewGroup, false) as RootView
    }

    interface ParentComponent { // Define dependencies required from your parent interactor here.
    }

    @dagger.Module
    abstract class Module {
        @RootScope
        @Binds
        abstract fun presenter(view: RootView): RootPresenter

        companion object {
            @RootScope
            @Provides
            fun loggedOutListener(rootInteractor: RootInteractor): LoggedOutInteractor.Listener {
                return rootInteractor.LoggedOutListener()
            }

            @RootScope
            @Provides
            fun router(
                component: Component,
                view: RootView,
                interactor: RootInteractor
            ): RootRouter {
                return RootRouter(
                    view,
                    interactor,
                    component,
                    LoggedOutBuilder(component),
                    LoggedInBuilder(component)
                )
            }
        }
    }

    @RootScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<RootInteractor>,
        LoggedOutBuilder.ParentComponent,
        LoggedInBuilder.ParentComponent, BuilderComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: RootInteractor): Builder

            @BindsInstance
            fun view(view: RootView): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun rootRouter(): RootRouter
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class RootScope
}