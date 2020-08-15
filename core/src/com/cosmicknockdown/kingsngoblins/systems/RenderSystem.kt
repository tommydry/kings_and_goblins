package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.cosmicknockdown.kingsngoblins.KNG
import com.cosmicknockdown.kingsngoblins.components.TextureComponent
import com.cosmicknockdown.kingsngoblins.components.TransformComponent


class RenderSystem(val batch: SpriteBatch) : IteratingSystem(Family.all(TransformComponent::class.java, TextureComponent::class.java).get()) {
    val renderArray: Array<Entity> = Array()

    val transformM: ComponentMapper<TransformComponent> = ComponentMapper.getFor(TransformComponent::class.java)
    val rendererM: ComponentMapper<TextureComponent> = ComponentMapper.getFor(TextureComponent::class.java)

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        renderArray.sort { o1, o2 ->
            val transform1 = transformM.get(o1)
            val transform2 = transformM.get(o2)
            transform2.zIndex - transform1.zIndex
        }

        batch.begin()

        renderArray.forEach { entity ->
            val transform = transformM.get(entity)
            val tex = rendererM.get(entity)
            val width = tex.region.regionWidth / KNG.PPM
            val height = tex.region.regionHeight / KNG.PPM
            val originX = width * 0.5f
            val originY = height * 0.5f
            batch.draw(tex.region, transform.pos.x - originX, transform.pos.y - originY, originX, originY, width, height, transform.scale.x, transform.scale.y, transform.rotation * MathUtils.radiansToDegrees)
        }

        batch.end()
        renderArray.clear()

    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        renderArray.add(entity)
    }
}