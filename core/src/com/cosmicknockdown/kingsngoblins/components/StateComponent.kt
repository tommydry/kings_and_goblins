package com.cosmicknockdown.kingsngoblins.components

import com.badlogic.ashley.core.Component

class StateComponent(var stateTime: Float = 0F, var state: Int = 0) : Component {

    companion object {
        const val IDLE = 0
        const val UP = 1
        const val DOWN = 2
        const val LEFT = 3
        const val RIGHT = 4

        const val MOVE_UP = 5
        const val MOVE_DOWN = 6
        const val MOVE_LEFT = 7
        const val MOVE_RIGHT = 8
    }
}