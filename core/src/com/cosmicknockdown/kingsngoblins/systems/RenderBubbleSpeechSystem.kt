package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
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

class RenderBubbleSpeechSystem(
    val label: Label,
    val stage: Stage,
    val camera: OrthographicCamera,
    val viewport: Viewport,
    val renderer: OrthogonalTiledMapRenderer
) : IteratingSystem(
    Family.all(
        BubbleSpeechComponent::class.java,
        TransformComponent::class.java
    ).get()
) {

    private val renderArray: Array<Entity> = Array()

    private var lastTimeBubbleSpeechShowed = System.currentTimeMillis()

    private val transformM: ComponentMapper<TransformComponent> =
        ComponentMapper.getFor(TransformComponent::class.java)

    private val bubbleSpeechM: ComponentMapper<BubbleSpeechComponent> =
        ComponentMapper.getFor(BubbleSpeechComponent::class.java)

    private val font: BitmapFont = BitmapFont().apply {
        data.scale(0.1f)
    }


    override fun update(deltaTime: Float) {
        super.update(deltaTime)

//        if (Math.abs((lastTimeBubbleSpeechShowed - System.currentTimeMillis())) > BUBBLE_SHOW_PERIOD_MS) {
//            lastTimeBubbleSpeechShowed = System.currentTimeMillis()
//
//
//            renderArray.sort { o1, o2 ->
//                val transformComponent1 = transformM.get(o1)
//                val transformComponent2 = transformM.get(o2)
//                transformComponent2.zIndex - transformComponent1.zIndex
//            }
//
//            batch.begin()
//            renderArray.forEach {
//                val transformComponent = transformM.get(it)
//                label.isVisible = true
//                val screenToStageCoordinates = stage.screenToStageCoordinates(transformComponent.pos)
//                val pos = transformComponent.pos
//                label.setPosition(pos.x, pos.y)
//            }
//            batch.end()
//        }
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val transformComponent = transformM.get(entity)

        val pos = transformComponent.pos
//        val pos3 = Vector3(pos, 0f)
//        val unproject = camera.project(pos3)
//        val stageCoordinates = stage.screenToStageCoordinates(Vector2(unproject.x, unproject.y))
        label.setPosition(pos.x, pos.y)

//        val toScreenCoordinates = viewport.toScreenCoordinates(pos, camera.projection)

//        label.setPosition(toScreenCoordinates.x, toScreenCoordinates.y)
//        val stageCoordinates = stage.screenToStageCoordinates(toScreenCoordinates)
//        label.setPosition(pos.x, pos.y)
//        println("here")
//        renderArray.add(entity)

//        batch.begin()
//        font.draw(batch, "Booch LOH", transformComponent.pos.x, transformComponent.pos.y)
//        batch.end()
    }

    companion object {
        const val BUBBLE_SHOW_PERIOD_MS = 3 * 1000 // 3 SEC
    }

}