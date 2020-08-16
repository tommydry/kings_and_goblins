package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.cosmicknockdown.kingsngoblins.components.PositionComponent
import com.cosmicknockdown.kingsngoblins.components.TransformComponent
import com.cosmicknockdown.kingsngoblins.components.VelocityComponent

class MovementSystem : EntitySystem() {
    var entities: ImmutableArray<Entity>? = null
    var pm: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
    var vm: ComponentMapper<VelocityComponent> = ComponentMapper.getFor(VelocityComponent::class.java)
    var tm: ComponentMapper<TransformComponent> = ComponentMapper.getFor(TransformComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent::class.java, VelocityComponent::class.java, TransformComponent::class.java).get())
    }

    override fun update(deltaTime: Float) {
        entities!!.forEach {
            val velocityComponent = vm.get(it)
            val positionComponent = pm.get(it)
            val transformComponent = tm.get(it)

            positionComponent.body.setLinearVelocity(velocityComponent.direction.x * 30, velocityComponent.direction.y * 30)

            transformComponent.pos.set(positionComponent.body.position)
        }
    }

}