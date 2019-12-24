package com.cosmicknockdown.kingsngoblins.ai

import com.badlogic.gdx.ai.pfa.*
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

class AStarPathFinding(var map: AStarMap) {
    lateinit var pathfinder: PathFinder<Node>
    lateinit var heuristic: Heuristic<Node>
    lateinit var connectionPath: GraphPath<Connection<Node>>

    init {
        pathfinder = IndexedAStarPathFinder<Node>(createGraph())
        connectionPath = DefaultGraphPath<Connection<Node>>()
        heuristic = object : Heuristic<Node> {
            override fun estimate(node: Node?, endNode: Node?): Float {
                return (Math.abs(endNode!!.x - node!!.x) + Math.abs(endNode!!.y - node!!.y)).toFloat()
            }
        }
    }

    companion object {
        var NEIGHBORHOOD = arrayOf<kotlin.Array<Int>>()

        init {
            NEIGHBORHOOD += arrayOf(-1, 0)
            NEIGHBORHOOD += arrayOf(0, -1)
            NEIGHBORHOOD += arrayOf(0, 1)
            NEIGHBORHOOD += arrayOf(1, 0)
        }
    }

    fun createGraph(): MyGraph {
        val myGraph = MyGraph(map)
        for (i in 0..map.width - 1) {
            for (y in 0..map.height - 1) {
                val node = map.map[i][y]
                if (!node.isWall) {
                    for (z in 0..NEIGHBORHOOD.size - 1) {
                        val neighborhoodX = NEIGHBORHOOD[z][0]
                        val neighborhoodY = NEIGHBORHOOD[z][1]
                        if (neighborhoodX >= 0 && neighborhoodX < map.width && neighborhoodY >= 0 && neighborhoodY < 0) {
                            val neighbor = map.map[i][y]
                            if (!neighbor.isWall) {
                                node.connections.add(DefaultConnection<Node>(node, neighbor))
                            }
                        }
                    }
                    node.connections.shuffle()
                }
            }
        }
        return myGraph
    }

    fun findNextNode(source: Vector2, target: Vector2): Node? {
        var sourceX = MathUtils.floor(source.x)
        var sourceY = MathUtils.floor(source.y)
        var targetX = MathUtils.floor(target.x)
        var targetY = MathUtils.floor(target.y)

        if (map == null ||
                sourceX < 0 || sourceX >= map.width
                || sourceY < 0 || sourceY >= map.height
                || targetX < 0 || targetX >= map.width
                || targetY < 0 || targetY >= map.height)
            return null

        val sourceNode = map.map[sourceX][sourceY]
        val targetNode = map.map[targetX][targetY]

        connectionPath.clear()
        pathfinder.searchConnectionPath(sourceNode, targetNode, heuristic, connectionPath)
        return if (connectionPath.count == 0) null else connectionPath.get(0).toNode
    }

    class MyGraph(var map: AStarMap) : IndexedGraph<Node> {

        //TODO check if we can replace it with inline methods
        override fun getConnections(fromNode: Node?): Array<Connection<Node>> {
            return fromNode!!.connections
        }

        override fun getIndex(node: Node?): Int {
            return node!!.index
        }

        override fun getNodeCount(): Int {
            return map.height * map.width
        }
    }
}