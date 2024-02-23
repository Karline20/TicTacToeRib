package com.owtoph.tictactoerib

import android.view.ViewGroup
import com.owtoph.tictactoerib.root.RootBuilder
import com.owtoph.tictactoerib.root.RootBuilder.ParentComponent
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter


class RootActivity : RibActivity() {

    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *> {
        val rootBuilder = RootBuilder(object : ParentComponent {})
        return rootBuilder.build(parentViewGroup)
    }
}