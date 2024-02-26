package com.owtoph.tictactoerib.root.loggedin.offgame

import com.uber.rib.core.ViewRouter




/**
 * Created by Karlen Legaspi
 */
/** Adds and removes children of [OffGameBuilder.OffGameScope].  */
class OffGameRouter(
    view: OffGameView, interactor: OffGameInteractor, component: OffGameBuilder.Component
) :
    ViewRouter<OffGameView, OffGameInteractor>(view, interactor, component)