package com.owtoph.tictactoerib.root.loggedin.tictactoe

import com.uber.rib.core.ViewRouter




/**
 * Created by Karlen Legaspi
 */

class TicTacToeRouter(
    view: TicTacToeView, interactor: TicTacToeInteractor, component: TicTacToeBuilder.Component
) :
    ViewRouter<TicTacToeView, TicTacToeInteractor>(view, interactor, component)