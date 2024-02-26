package com.owtoph.tictactoerib.root

import android.content.Intent
import com.uber.rib.workflow.core.Step
import com.uber.rib.workflow.core.Workflow

import java.io.Serializable




/**
 * Created by Karlen Legaspi
 */
/**
 * Represents a workflow that is launched starting at [RootActionableItem].
 *
 * @param <TWorkflowReturnType> the type of value returned by the workflow (if any).
 * @param <TDeepLinkModel> the type of deep link model used by this workflow.
</TDeepLinkModel></TWorkflowReturnType> */
abstract class RootWorkflow<TWorkflowReturnType, TDeepLinkModel : RootWorkflowModel> :
    Workflow<TWorkflowReturnType, RootActionableItem>, Serializable {
    /**
     * @return the model for the workflow.
     */
    val deepLinkModel: TDeepLinkModel

    /**
     * Constructor to create a [RootWorkflow] from an [Intent].
     *
     * @param deepLinkIntent the raw deep link [Intent] to parse for the workflow.
     */
    constructor(deepLinkIntent: Intent?) {
        deepLinkModel = parseDeepLinkIntent(deepLinkIntent)
    }

    /**
     * Constructor to create a [RootWorkflow] from a deep link model.
     *
     * @param deepLinkModel
     */
    constructor(deepLinkModel: TDeepLinkModel) {
        this.deepLinkModel = deepLinkModel
    }

    override fun getSteps(rootActionableItem: RootActionableItem): Step<TWorkflowReturnType, *> {
        return getSteps(rootActionableItem, deepLinkModel)
    }

    /**
     * @param rootActionableItem to create steps with.
     * @param deepLinkModel to create deep link data from.
     * @return steps to be performed for this workflow.
     */
    protected abstract fun getSteps(
        rootActionableItem: RootActionableItem, deepLinkModel: TDeepLinkModel
    ): Step<TWorkflowReturnType, *>

    /**
     * Responsible for turning a raw URI into a deep link model with a rigid schema.
     *
     * @param deepLinkIntent the raw deep link [Intent].
     * @return the deep link model.
     */
    protected abstract fun parseDeepLinkIntent(deepLinkIntent: Intent?): TDeepLinkModel
}