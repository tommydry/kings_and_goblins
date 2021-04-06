package com.cosmicknockdown.kingsngoblins.components

import com.badlogic.ashley.core.Component

class BubbleSpeechComponent() : Component {

    private val statements = listOf<String>(
        "Of course Katlin!",
        "Whoop ass and look good",
        "I run this whole ship",
        "I will rearrange his facial structure"
    )

    fun getRandomStatement(): String {
        return statements.random()
    }
}