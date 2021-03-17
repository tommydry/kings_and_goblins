package com.cosmicknockdown.kingsngoblins.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.cosmicknockdown.kingsngoblins.components.AnimationComponent
import com.cosmicknockdown.kingsngoblins.components.StateComponent
import com.cosmicknockdown.kingsngoblins.components.TextureComponent

class AnimationSystem : IteratingSystem(
    Family.all(
        TextureComponent::class.java,
        AnimationComponent::class.java,
        StateComponent::class.java
    ).get()
) {

    val textureM: ComponentMapper<TextureComponent> = ComponentMapper.getFor(TextureComponent::class.java)
    val animationM: ComponentMapper<AnimationComponent> = ComponentMapper.getFor(AnimationComponent::class.java)
    val stateM: ComponentMapper<StateComponent> = ComponentMapper.getFor(StateComponent::class.java)

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val tex = textureM.get(entity)
        val anim = animationM.get(entity)
        val state = stateM.get(entity)
        val textureRegion = anim.getKeyFrame(state.state)

        if ((state.state == StateComponent.MOVE_LEFT || state.state == StateComponent.LEFT) && !textureRegion.isFlipX)
            textureRegion.flip(true, false)

        if ((state.state == StateComponent.MOVE_RIGHT || state.state == StateComponent.RIGHT) && textureRegion.isFlipX)
            textureRegion.flip(true, false)

        tex.region.setRegion(textureRegion)
    }

}