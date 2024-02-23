package com.owtoph.tictactoerib.root

import com.uber.rib.core.ViewRouter




/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [RootBuilder.RootScope].  */
class RootRouter internal constructor(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component
) :
    ViewRouter<RootView, RootInteractor>(view, interactor, component)