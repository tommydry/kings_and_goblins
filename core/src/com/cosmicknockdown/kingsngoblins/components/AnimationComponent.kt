package com.cosmicknockdown.kingsngoblins.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.IntMap

class AnimationComponent(val animations: IntMap<Animation<TextureRegion>>) : Component {
    var startTime = 0f

    fun getKeyFrame(state: Int): TextureRegion {
        startTime += Gdx.graphics.deltaTime
        return animations.get(state).getKeyFrame(startTime, true)
    }
}