package com.owtoph.tictactoerib.root.loggedin

import com.owtoph.tictactoerib.root.RootView
import com.owtoph.tictactoerib.root.loggedin.dashboard.DashBoardBuilder
import com.owtoph.tictactoerib.root.loggedin.dashboard.DashBoardInteractor
import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope


/**
 * Created by Karlen Legaspi
 */

class LoggedInBuilder(dependency: ParentComponent) : Builder<LoggedInRouter, LoggedInBuilder.ParentComponent>(dependency) {
    /**
     * Builds a new [LoggedInRouter].
     *
     * @return a new [LoggedInRouter].
     */
    fun build(): LoggedInRouter {
        val interactor = LoggedInInteractor()
        val component = DaggerLoggedInBuilder_Component.builder()
            .parentComponent(dependency)
            .interactor(interactor)
            .build()
        return component.loggedinRouter()
    }

    interface ParentComponent {
        fun rootView(): RootView
    }

    @dagger.Module
    abstract class Module {

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
                    rootView
                )
            }

            @LoggedInScope
            @Provides
            fun listener(interactor: LoggedInInteractor): DashBoardInteractor.Listener {
                return interactor.DashBoardListener()
            }

        }
    }


    @LoggedInScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<LoggedInInteractor>, BuilderComponent,
        DashBoardBuilder.ParentComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: LoggedInInteractor): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
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