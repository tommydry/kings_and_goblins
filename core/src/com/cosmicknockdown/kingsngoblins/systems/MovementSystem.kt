package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.cosmicknockdown.kingsngoblins.components.PositionComponent
import com.cosmicknockdown.kingsngoblins.components.StateComponent
import com.cosmicknockdown.kingsngoblins.components.TransformComponent
import com.cosmicknockdown.kingsngoblins.components.VelocityComponent
import javax.swing.plaf.nimbus.State

class MovementSystem : EntitySystem() {
    var entities: ImmutableArray<Entity>? = null
    var pm: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
    var vm: ComponentMapper<VelocityComponent> = ComponentMapper.getFor(VelocityComponent::class.java)
    var tm: ComponentMapper<TransformComponent> = ComponentMapper.getFor(TransformComponent::class.java)
    var sm: ComponentMapper<StateComponent> = ComponentMapper.getFor(StateComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
            Family.all(
                PositionComponent::class.java,
                VelocityComponent::class.java,
                TransformComponent::class.java
            ).get()
        )
    }

    override fun update(deltaTime: Float) {
        entities?.forEach {
            val velocityComponent = vm.get(it)
            val positionComponent = pm.get(it)
            val transformComponent = tm.get(it)
            val stateComponent = sm.get(it)

            with(stateComponent) {
                val xVelocity = velocityComponent.direction.x
                val yVelocity = velocityComponent.direction.y
                state = when {
                    xVelocity > 0 -> StateComponent.MOVE_RIGHT
                    xVelocity < 0 -> StateComponent.MOVE_LEFT
                    yVelocity > 0 || yVelocity < 0 -> {
                        if (state in listOf(StateComponent.MOVE_RIGHT, StateComponent.RIGHT)) {
                            StateComponent.MOVE_RIGHT
                        } else if (state in listOf(StateComponent.MOVE_LEFT, StateComponent.LEFT)) {
                            StateComponent.MOVE_LEFT
                        } else {
                            state
                        }
                    }
                    state == StateComponent.MOVE_RIGHT -> StateComponent.RIGHT
                    state == StateComponent.MOVE_LEFT -> StateComponent.LEFT
                    else -> state
                }
            }

            positionComponent.body.setLinearVelocity(
                velocityComponent.direction.x * 15,
                velocityComponent.direction.y * 15
            )

            transformComponent.pos.set(positionComponent.body.position)

        }
    }

}