package com.cosmicknockdown.kingsngoblins.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeType
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.*
import com.cosmicknockdown.kingsngoblins.KNG
import com.cosmicknockdown.kingsngoblins.components.*
import com.cosmicknockdown.kingsngoblins.systems.*
import com.cosmicknockdown.kingsngoblins.utils.WorldBuilder

const val text = "Of course Katlin!!!"
class GameScreen
(game: KNG) : Screen {

//    private val stage: Stage = Stage(ScreenViewport())
    private var renderer: OrthogonalTiledMapRenderer
    private var map: TiledMap = TmxMapLoader().load(KNG.FIRST_LEVEL_MAP_PATH)
    private var camera: OrthographicCamera = OrthographicCamera()
    private var viewport: Viewport =
        ScalingViewport(
            Scaling.fill,
            KNG.GAME_WIDTH_IN_TILES,
            KNG.GAME_HEIGHT_IN_TILES,
            camera
        )

    private var batch: SpriteBatch = SpriteBatch()
    private var world: World
    private var box2DDebugRenderer: Box2DDebugRenderer
    private var engine: Engine
    private val stage: Stage = Stage(viewport)
    private var label: Label
    private var bitmapFont: BitmapFont

    init {
        renderer = OrthogonalTiledMapRenderer(map, 1 / 16f, batch)
        renderer.setView(camera)

        world = World(Vector2.Zero, true)
        box2DDebugRenderer = Box2DDebugRenderer()
        val worldBuilder = WorldBuilder(map, world)
        worldBuilder.buildWalls()

        val freeTypeFontGenerator = FreeTypeFontGenerator(Gdx.files.internal("fonts/DelaGothicOne-Regular.ttf"))
        val freeTypeFontParameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = 24
            color = Color.ORANGE
            borderWidth = 1f
        }
        bitmapFont = freeTypeFontGenerator.generateFont(freeTypeFontParameter)
        freeTypeFontGenerator.dispose()

        val labelStyle = Label.LabelStyle()
        labelStyle.font = bitmapFont
        labelStyle.fontColor = Color.ORANGE
        labelStyle.font.setUseIntegerPositions(false)

        label = Label(text, labelStyle)

        label.setWrap(true)
        label.setAlignment(Align.center)
        label.setSize(4f, 2f)

        while (label.prefHeight > label.height) {
            label.fontScaleX = label.fontScaleX * 0.75f
            label.fontScaleY = label.fontScaleY * 0.75f
        }

        val playerEntity = createPlayer(worldBuilder, game)

        engine = Engine().apply {
            addEntity(playerEntity)

            addSystem(AnimationSystem())
            addSystem(MovementSystem())
            addSystem(InputSystem())
            addSystem(RenderSystem(batch))
            addSystem(RenderBubbleSpeechSystem(label, stage, camera, viewport, renderer))
        }

        stage.addActor(label)
        stage.isDebugAll = true
    }

    private fun createPlayer(
        worldBuilder: WorldBuilder,
        game: KNG
    ): Entity {
        val playerSpot = map.layers.get(KNG.FIRST_LEVEL_CHARACTER_SPOT_LAYER_NAME).objects.first()
        val playerBody = worldBuilder.buildPlayer(playerSpot as RectangleMapObject)
        val playerTexture = game.assetManager.get(KNG.PLAYER_ATLAS_PATH, Texture::class.java)
        return Entity().apply {
            add(AnimationComponent(game.getMainCharacterAnimation()))
            add(PositionComponent(playerBody))
            add(VelocityComponent(Vector2(0f, 0f)))
            add(TransformComponent(0f, 0f))
            add(TextureComponent(TextureRegion(playerTexture, 0, 6, 16, 16)))
            add(StateComponent(state = StateComponent.IDLE))
            add(BubbleSpeechComponent())
        }
    }

    override fun hide() {
        TODO("Not yet implemented")
    }

    override fun show() {
//        camera.translate(viewport.worldWidth / 2, viewport.worldHeight / 2)
        camera.update()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(28f / 255f, 17f / 255f, 23f / 255f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.setView(camera)
        renderer.render()

        engine.update(delta)
        world.step(1 / 60f, 8, 3)

        stage.act(delta)
        stage.draw()

//        box2DDebugRenderer.render(world, camera.combined)
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
        stage.dispose()
    }

}