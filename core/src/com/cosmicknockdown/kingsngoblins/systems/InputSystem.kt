package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.cosmicknockdown.kingsngoblins.components.PositionComponent
import com.cosmicknockdown.kingsngoblins.components.VelocityComponent


class InputSystem : EntitySystem() {
    var entities: ImmutableArray<Entity>? = null
    var vm: ComponentMapper<VelocityComponent> = ComponentMapper.getFor(VelocityComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent::class.java, VelocityComponent::class.java).get())
    }

    override fun update(deltaTime: Float) {
        entities?.forEach {
            val velocityComponent = vm.get(it)
            velocityComponent.apply {
                direction.y = if (Gdx.input.isKeyPressed(Input.Keys.W)) 1f else if (Gdx.input.isKeyPressed(Input.Keys.S)) -1f else 0f
                direction.x = if (Gdx.input.isKeyPressed(Input.Keys.D)) 1f else if (Gdx.input.isKeyPressed(Input.Keys.A)) -1f else 0f
            }
        }
    }
}