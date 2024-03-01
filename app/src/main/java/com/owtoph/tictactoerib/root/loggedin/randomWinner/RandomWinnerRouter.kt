package com.owtoph.tictactoerib.root.loggedin.randomWinner

import com.uber.rib.core.ViewRouter




/**
 * Created by Karlen Legaspi
 */
/**
 * Adds and removes children of [RandomWinnerBuilder.RandomWinnerScope].
 *
 *
 * TODO describe the possible child configurations of this scope.
 */
class RandomWinnerRouter(
    view: RandomWinnerView,
    interactor: RandomWinnerInteractor,
    component: RandomWinnerBuilder.Component
) :
    ViewRouter<RandomWinnerView, RandomWinnerInteractor>(view, interactor, component)