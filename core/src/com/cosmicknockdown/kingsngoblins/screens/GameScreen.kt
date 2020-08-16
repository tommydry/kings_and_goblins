package com.cosmicknockdown.kingsngoblins.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.*
import com.cosmicknockdown.kingsngoblins.KNG
import com.cosmicknockdown.kingsngoblins.components.*
import com.cosmicknockdown.kingsngoblins.systems.AnimationSystem
import com.cosmicknockdown.kingsngoblins.systems.InputSystem
import com.cosmicknockdown.kingsngoblins.systems.MovementSystem
import com.cosmicknockdown.kingsngoblins.systems.RenderSystem
import com.cosmicknockdown.kingsngoblins.utils.WorldBuilder

class GameScreen : Screen {

    private var renderer: OrthogonalTiledMapRenderer
    private var map: TiledMap
    private var camera: OrthographicCamera
    private var viewport: Viewport
    private var batch: SpriteBatch
    private var world: World
    private var box2DDebugRenderer: Box2DDebugRenderer
    private var engine: Engine

    constructor(game: Game) {
        camera = OrthographicCamera()
        viewport = ScalingViewport(Scaling.fit, 45f, 43f, camera)
        map = TmxMapLoader().load("maps/dungeon.tmx")
        batch = SpriteBatch()
        renderer = OrthogonalTiledMapRenderer(map, 1 / 16f, batch)
        renderer.setView(camera)
        world = World(Vector2.Zero, true)
        box2DDebugRenderer = Box2DDebugRenderer()
        val worldBuilder = WorldBuilder(map, world)

        worldBuilder.buildWalls()

        val first = map.layers.get("character_spot").objects.first()

        val playerEntity = Entity()
        val body = worldBuilder.buildPlayer(first as RectangleMapObject)
        playerEntity.add(PositionComponent(body))
        playerEntity.add(VelocityComponent(Vector2(0f, 0f)))
        playerEntity.add(TransformComponent(body.position.x, body.position.y))
        val texture = (game as KNG).assetManager.get("textures/player/player_atlas_orange.png", Texture::class.java)
        //TODO: Remove top space from player_atlas_*.png (have to use 6px space from top)
        playerEntity.add(TextureComponent(TextureRegion(texture, 0, 6, 16, 16)))

        engine = Engine()
        engine.addEntity(playerEntity)
        engine.addSystem(MovementSystem())
        engine.addSystem(InputSystem())
        engine.addSystem(AnimationSystem())
        engine.addSystem(RenderSystem(batch))
    }

    override fun hide() {
        //do nothing
    }

    override fun show() {
        camera.translate(viewport.worldWidth / 2, viewport.worldHeight / 2)
        camera.update()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(28f / 255f, 17f / 255f, 23f / 255f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        renderer.setView(camera)
        renderer.render()
        batch.projectionMatrix = camera.combined
        engine.update(delta)
        world.step(1 / 60f, 8, 3)
        box2DDebugRenderer.render(world, camera.combined)
    }

    override fun pause() {
        //do nothing
    }

    override fun resume() {
        //do nothing
    }

    override fun resize(width: Int, height: Int) {
        //do nothing
        viewport.update(width, height)
    }

    override fun dispose() {
        //do nothing
    }
}