package com.cosmicknockdown.kingsngoblins.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.cosmicknockdown.kingsngoblins.KNG
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTextButton

class MenuScreen : Screen {
    var backgroundMusic: Music
    var background: Sprite
    var stage: Stage
    var startButton: VisTextButton

    constructor(game: KNG) {
        val extendViewport = ExtendViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        stage = Stage(extendViewport)
        Gdx.input.inputProcessor = stage
        background = Sprite(game.assetManager.get<Texture>("textures/menu/menu_bg.png"))
        VisUI.load(VisUI.SkinScale.X1)
        startButton = VisTextButton("StartGame", object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.showGameScreen()
            }
        })
        startButton.width = stage.width * 0.3f
        startButton.height = stage.height * 0.1f
        startButton.label.style.fontColor = Color.GRAY
        startButton.color = Color.LIGHT_GRAY
        startButton.setPosition(stage.width * 0.5f, stage.height * 0.5f, Align.center)

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/bg.wav"))
        backgroundMusic.setLooping(true)
    }

    override fun hide() {
    }

    override fun show() {
        backgroundMusic.play()
        stage.clear()
        stage.addActor(startButton)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(255f, 0.2f, 0.2f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.batch.begin()
        stage.batch.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        stage.batch.end()
        stage.draw()
    }

    override fun pause() {
        backgroundMusic.pause()
    }

    override fun resume() {
        backgroundMusic.play()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
        VisUI.dispose()
        backgroundMusic.dispose()
    }
}
