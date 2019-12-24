package com.cosmicknockdown.kingsngoblins.ai

import com.badlogic.gdx.ai.pfa.Connection
import com.badlogic.gdx.utils.Array

class Node(var x: Int, var y: Int, var index: Int, var isWall: Boolean, var connections: Array<Connection<Node>>) {

    constructor(map: AStarMap, x: Int, y: Int) : this(x, y, x * map.height, false, Array<Connection<Node>>())
}