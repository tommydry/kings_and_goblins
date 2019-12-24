package com.cosmicknockdown.kingsngoblins.ai

class AStarMap(var width: Int, var height: Int) {
    var map = arrayOf<Array<Node>>()

    init {
        for (i in 0..width - 1) {
            var raw = arrayOf<Node>()
            for (j in 0..height - 1) {
                raw += Node(this, i, j)
            }
            map += raw
        }
    }



}