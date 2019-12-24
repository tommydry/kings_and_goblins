package com.cosmicknockdown.kingsngoblins

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.cosmicknockdown.kingsngoblins.screens.GameScreen
import com.cosmicknockdown.kingsngoblins.screens.MenuScreen

class KNG() : Game() {
    val menu: Int = 0
    val game: Int = 1

    var state: Int = -1
        set(value) {
            field = value
            if (screen != null)
                screen.dispose()
            if (state == menu)
                setScreen(MenuScreen(this))
            else if (state == game)
                setScreen(GameScreen(this))
        }

    override fun create() {
        state = game
    }

    companion object {
        const val PPM: Float = 16f

        const val NOTHING_BIT: Short = 0
        const val WALL_BIT: Short = 1
        const val PLAYER_BIT: Short = 1 shl 1
        const val ENEMY_BIT: Short = 1 shl 2

        private lateinit var assetManager: AssetManager

        fun init() {
            assetManager = AssetManager()

        }
    }

}