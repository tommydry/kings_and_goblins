package com.cosmicknockdown.kingsngoblins.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.cosmicknockdown.kingsngoblins.KNG
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt
import kotlin.system.measureNanoTime

class LoadingScreen(game: Game) : Screen {

    private val stage = Stage(ScreenViewport())
    private var progressLabel: Label
    private val game: KNG = game as KNG
    var readyTime: Long = -1L

    init {
        //input from stage
        Gdx.input.inputProcessor = stage

        //init label
        val labelStyle = Label.LabelStyle()
        labelStyle.font = BitmapFont(Gdx.files.internal("fonts/main.fnt"), false)
        labelStyle.fontColor = Color.ORANGE
        progressLabel = Label("Progress\n", labelStyle)
        progressLabel.setPosition((stage.width * 0.5f) - (progressLabel.prefWidth * 0.5f), stage.height * 0.5f)
        progressLabel.setAlignment(Align.center)

        stage.addActor(progressLabel)
    }

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0.2f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        val assetManager = game.assetManager

        if (readyTime == -1L) {
            if (game.assetManager.update()) {
                readyTime = System.currentTimeMillis()
            }
            progressLabel.setText("Progress\n${(assetManager.progress * 100).toInt()}")
        } else {
            if ((System.currentTimeMillis() - readyTime) > 1000) {
                game.showGameScreen()
            }
        }

        stage.act(delta)
        stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
    }
}