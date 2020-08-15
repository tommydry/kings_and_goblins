package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.cosmicknockdown.kingsngoblins.components.AnimationComponent
import com.cosmicknockdown.kingsngoblins.components.StateComponent
import com.cosmicknockdown.kingsngoblins.components.TextureComponent

class AnimationSystem : IteratingSystem(Family.all(TextureComponent::class.java, AnimationComponent::class.java, StateComponent::class.java).get()) {
    val textureM: ComponentMapper<TextureComponent> = ComponentMapper.getFor(TextureComponent::class.java)
    val animationM: ComponentMapper<AnimationComponent> = ComponentMapper.getFor(AnimationComponent::class.java)
    val stateM: ComponentMapper<StateComponent> = ComponentMapper.getFor(StateComponent::class.java)

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val tex = textureM.get(entity)
        val anim = animationM.get(entity)
        val state = stateM.get(entity)
        tex.region.setRegion(anim.animations.get(state.state).getKeyFrame(state.stateTime))
    }
}