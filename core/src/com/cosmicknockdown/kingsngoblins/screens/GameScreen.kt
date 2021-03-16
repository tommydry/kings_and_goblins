package com.cosmicknockdown.kingsngoblins.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
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

class GameScreen
(game: KNG) : Screen {

    private var renderer: OrthogonalTiledMapRenderer
    private var map: TiledMap
    private var camera: OrthographicCamera = OrthographicCamera()
    private var viewport: Viewport
    private var batch: SpriteBatch
    private var world: World
    private var box2DDebugRenderer: Box2DDebugRenderer
    private var engine: Engine

    init {
        //TODO MOVE ALL LITERALS TO CONSTANTS
        viewport = ScalingViewport(Scaling.fill, 45f, 43f, camera)

        map = TmxMapLoader().load(KNG.FIRST_LEVEL_MAP_PATH)

        batch = SpriteBatch()
        renderer = OrthogonalTiledMapRenderer(map, 1 / 16f, batch)
        renderer.setView(camera)

        world = World(Vector2.Zero, true)
        box2DDebugRenderer = Box2DDebugRenderer()
        val worldBuilder = WorldBuilder(map, world)
        worldBuilder.buildWalls()

        val playerSpot = map.layers.get(KNG.FIRST_LEVEL_CHARACTER_SPOT_LAYER_NAME).objects.first()
        val playerBody = worldBuilder.buildPlayer(playerSpot as RectangleMapObject)
        val playerTexture = game.assetManager.get(KNG.PLAYER_ATLAS_PATH, Texture::class.java)
        val playerEntity = Entity().apply {
            add(AnimationComponent(game.getMainCharacterAnimation()))
            add(PositionComponent(playerBody))
            add(VelocityComponent(Vector2(0f, 0f)))
            add(TransformComponent(playerBody.position.x, playerBody.position.y))
            add(TextureComponent(TextureRegion(playerTexture, 0, 6, 16, 16)))
            add(StateComponent(state = StateComponent.IDLE))
        }
        engine = Engine().apply {
            addEntity(playerEntity)

            addSystem(AnimationSystem())
            addSystem(MovementSystem())
            addSystem(InputSystem())
            addSystem(RenderSystem(batch))
        }
    }

    override fun hide() {
        TODO("Not yet implemented")
    }

    override fun show() {
        camera.translate(viewport.worldWidth / 2, viewport.worldHeight / 2)
        camera.update()
        batch.projectionMatrix = camera.combined
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(28f / 255f, 17f / 255f, 23f / 255f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.setView(camera)
        renderer.render()

        engine.update(delta)
        world.step(1 / 60f, 8, 3)

        box2DDebugRenderer.render(world, camera.combined)
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }

}