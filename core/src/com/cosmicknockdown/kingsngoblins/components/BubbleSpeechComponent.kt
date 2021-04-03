package com.cosmicknockdown.kingsngoblins.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.scenes.scene2d.ui.Label

class BubbleSpeechComponent() : Component {

    private val statements = listOf<String>(
        "Hmmmm...",
        "What going on here?",
        "I'm so epic",
        "There is no one cooler than me",
        "Feel strange"
    )

    fun getRandomStatement(): String {
        return statements.random()
    }
}