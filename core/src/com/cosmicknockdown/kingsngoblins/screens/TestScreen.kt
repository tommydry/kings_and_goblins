package com.cosmicknockdown.kingsngoblins.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.cosmicknockdown.kingsngoblins.KNG

class TestScreen(game: KNG) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()
    private var viewport: Viewport
    private var batch: SpriteBatch
    private var walkAnimation: Animation<TextureRegion>
    private var playerTextureRegion: TextureRegion
    private var stateTime = 0f

    init {
        viewport = ScalingViewport(Scaling.fit, 45f, 43f, camera)
        batch = SpriteBatch()
        val playerTexture = game.assetManager.get("textures/player/player_atlas_orange.png", Texture::class.java)
        val split = TextureRegion.split(playerTexture, playerTexture.width / 7, playerTexture.height / 4)
        walkAnimation = Animation<TextureRegion>(0.075f, com.badlogic.gdx.utils.Array(arrayOf(split[2][0], split[2][1], split[2][2], split[2][3])))
        playerTextureRegion = TextureRegion(playerTexture, 0, 6, 16, 16)
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(28f / 255f, 17f / 255f, 23f / 255f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stateTime += Gdx.graphics.deltaTime
        val currentFrame = walkAnimation.getKeyFrame(stateTime, true)
        batch.begin()
        batch.draw(currentFrame, 16f, 16f)
        batch.end()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }
}