package com.owtoph.tictactoerib.root.loggedout

import android.view.LayoutInflater
import android.view.ViewGroup
import com.owtoph.tictactoerib.R
import com.owtoph.tictactoerib.root.loggedout.LoggedOutBuilder.LoggedOutScope
import com.owtoph.tictactoerib.root.loggedout.LoggedOutInteractor.LoggedOutPresenter
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope


/**
 * Created by Karlen Legaspi
 */
/** Builder for the [LoggedOutScope].  */
/** Builder for the [LoggedOutScope].  */
/** Builder for the [LoggedOutScope].  */
class LoggedOutBuilder(dependency: ParentComponent) :
    ViewBuilder<LoggedOutView, LoggedOutRouter, LoggedOutBuilder.ParentComponent>(dependency) {
    /**
     * Builds a new [LoggedOutRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [LoggedOutRouter].
     */
    fun build(parentViewGroup: ViewGroup): LoggedOutRouter {
        val view = createView(parentViewGroup)
        val interactor = LoggedOutInteractor()
        val component = DaggerLoggedOutBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.loggedoutRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): LoggedOutView {
        return inflater.inflate(R.layout.logged_out_rib, parentViewGroup, false) as LoggedOutView
    }

    interface ParentComponent {
        fun listener(): LoggedOutInteractor.Listener
    }

    @dagger.Module
    abstract class Module {
        @LoggedOutScope
        @Binds
        abstract fun presenter(view: LoggedOutView): LoggedOutPresenter

        companion object {
            @LoggedOutScope
            @Provides
            fun router(
                component: Component, view: LoggedOutView, interactor: LoggedOutInteractor
            ): LoggedOutRouter {
                return LoggedOutRouter(view, interactor, component)
            } // TODO: Create provider methods for dependencies created by this Rib. These should be static.
        }
    }

    @LoggedOutScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<LoggedOutInteractor>, BuilderComponent {
        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: LoggedOutInteractor): Builder

            @BindsInstance
            fun view(view: LoggedOutView): Builder
            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun loggedoutRouter(): LoggedOutRouter
    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class LoggedOutScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class LoggedOutInternal
}