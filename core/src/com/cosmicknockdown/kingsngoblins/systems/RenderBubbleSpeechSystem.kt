package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.Viewport
import com.cosmicknockdown.kingsngoblins.components.BubbleSpeechComponent
import com.cosmicknockdown.kingsngoblins.components.TransformComponent
import kotlin.math.abs

class RenderBubbleSpeechSystem(
    val label: Label
) : IteratingSystem(
    Family.all(
        BubbleSpeechComponent::class.java,
        TransformComponent::class.java
    ).get()
) {

    private var lastTimeBubbleSpeechShowed = 0L

    private val transformM: ComponentMapper<TransformComponent> =
        ComponentMapper.getFor(TransformComponent::class.java)

    private val bubbleSpeechM: ComponentMapper<BubbleSpeechComponent> =
        ComponentMapper.getFor(BubbleSpeechComponent::class.java)

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        if (abs((lastTimeBubbleSpeechShowed - System.currentTimeMillis())) > BUBBLE_SHOW_PERIOD_MS) {
            label.isVisible = false
        }
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val transformComponent = transformM.get(entity)
        val bubbleSpeechComponent = bubbleSpeechM.get(entity)
        val pos = transformComponent.pos
        label.setPosition(pos.x, pos.y)
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            label.setText(bubbleSpeechComponent.getRandomStatement())
            label.fontScaleX = 1f
            label.fontScaleY = 1f
            updateLabelHeight()
            lastTimeBubbleSpeechShowed = System.currentTimeMillis()
            label.isVisible = true
        }
    }

    private fun updateLabelHeight() {
        while (label.prefHeight > label.height) {
            label.fontScaleX = label.fontScaleX * 0.75f
            label.fontScaleY = label.fontScaleY * 0.75f
        }
    }

    companion object {
        const val BUBBLE_SHOW_PERIOD_MS = 3 * 1000 // 3 SEC
    }

}