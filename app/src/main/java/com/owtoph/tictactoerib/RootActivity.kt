package com.owtoph.tictactoerib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.google.common.base.Optional
import com.owtoph.tictactoerib.root.RootBuilder
import com.owtoph.tictactoerib.root.RootBuilder.ParentComponent
import com.owtoph.tictactoerib.root.RootInteractor
import com.owtoph.tictactoerib.root.RootWorkflow
import com.owtoph.tictactoerib.root.WorkflowFactory
import com.uber.autodispose.AutoDispose
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer


/** The sample app's single activity.  */
class RootActivity : RibActivity() {

    private var rootInteractor: RootInteractor? = null

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *> {
        val rootBuilder = RootBuilder(object : ParentComponent {})
        val router = rootBuilder.build(parentViewGroup)
        rootInteractor = router.interactor
        return router
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent != null) {
            handleDeepLink(intent)
        }
    }

    private fun handleDeepLink(intent: Intent) {
        val rootWorkflow = WorkflowFactory().getWorkflow(intent)
        rootInteractor?.let {
            rootWorkflow?.createSingle(it)?.subscribe { optional ->
                // Handle the optional value here
            }?.let {
                compositeDisposable.add(
                    it
                )
            }
        }
    }


    private inner class RootReturnValue
}