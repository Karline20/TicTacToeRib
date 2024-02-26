package com.owtoph.tictactoerib.root

import android.content.Intent


/**
 * Created by Karlen Legaspi
 */


class WorkflowFactory {
    fun getWorkflow(intent: Intent?): RootWorkflow<*, *>? {
        // If this was a real app you would likely write a pattern for each workflow object to
        // independently declare which intent it applied to. Then you would pick the first match.
        // Instead lets just do some simple if-else branches here.
        return if (intent != null && intent.data != null) {
            // TODO: return a workflow here
            null
        } else null
    }
}