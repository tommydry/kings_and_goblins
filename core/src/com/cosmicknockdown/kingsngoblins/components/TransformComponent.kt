package com.cosmicknockdown.kingsngoblins.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import java.util.*

class TransformComponent(
    val pos: Vector2 = Vector2(),
    val scale: Vector2 = Vector2(1F, 1F),
    val zIndex: Int,
    val sclX: Float,
    val sclY: Float,
    val rotation: Float
) : Component {

    constructor(
        x: Float,
        y: Float,
        scale: Vector2
    ) : this(
        Vector2(x, y),
        scale,
        0,
        1.0f,
        1.0f,
        0F
    )

    constructor(
        x: Float,
        y: Float
    ) : this(
        x,
        y,
        Vector2(
            1F,
            1F
        )
    )
}