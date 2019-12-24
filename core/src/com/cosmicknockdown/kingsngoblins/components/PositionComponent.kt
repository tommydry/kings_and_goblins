package com.cosmicknockdown.kingsngoblins.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body

data class PositionComponent(var body: Body) : Component