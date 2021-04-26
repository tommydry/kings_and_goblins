package com.cosmicknockdown.kingsngoblins.utils

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.*
import com.cosmicknockdown.kingsngoblins.KNG

class WorldBuilder {
    private val tiledMap: TiledMap
    private var world: World

    constructor(tiledMap: TiledMap, world: World) {
        this.tiledMap = tiledMap
        this.world = world
    }

    fun buildWalls() {
        val mapLayers = tiledMap.layers
        mapLayers.get("walls").objects.forEach {
            val rectangle = (it as RectangleMapObject).rectangle
            correctRectangle(rectangle)
            createRectangleBody(rectangle, BodyDef.BodyType.StaticBody, Filter().apply {
                categoryBits = KNG.WALL_BIT
                maskBits = (KNG.PLAYER_BIT + KNG.ENEMY_BIT).toShort()
            })
        }
    }

    fun buildPlayer(mapObject: RectangleMapObject): Body {
        val rectangle = mapObject.rectangle
        correctRectangle(rectangle)
        return createRectangleBody(rectangle, BodyDef.BodyType.DynamicBody, Filter().apply {
            categoryBits = KNG.PLAYER_BIT
            maskBits = (KNG.WALL_BIT + KNG.ENEMY_BIT).toShort()
        })
    }

    fun buildTreasure(mapObject: RectangleMapObject): Body {
        val rectangle = mapObject.rectangle
        correctRectangle(rectangle)
        return createRectangleBody(rectangle, BodyDef.BodyType.StaticBody, Filter().apply {
            categoryBits = KNG.WALL_BIT
            maskBits = (KNG.PLAYER_BIT + KNG.ENEMY_BIT).toShort()
        })
    }

    private fun createRectangleBody(rectangle: Rectangle, bodyType: BodyDef.BodyType, fixtureDefFilter: Filter): Body {
        var bodyDef = BodyDef()
        bodyDef.apply {
            type = bodyType
            position.x = rectangle.x + rectangle.width * 0.5f
            position.y = rectangle.y + rectangle.height * 0.5f
        }

        val body = world.createBody(bodyDef)
        val polygonShape = PolygonShape()
        polygonShape.setAsBox(rectangle.width * 0.5f, rectangle.height * 0.5f)
        val fixtureDef = FixtureDef()
        fixtureDef.apply {
            shape = polygonShape
            filter.apply {
                categoryBits = fixtureDefFilter.categoryBits
                maskBits = fixtureDefFilter.maskBits
            }
        }
        body.createFixture(fixtureDef)
        polygonShape.dispose()
        return body
    }

    fun correctRectangle(rectangle: Rectangle) {
        rectangle.apply {
            x /= KNG.PPM
            y /= KNG.PPM
            width /= KNG.PPM
            height /= KNG.PPM
        }
    }


}