package com.cosmicknockdown.kingsngoblins

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Timer
import com.cosmicknockdown.kingsngoblins.screens.GameScreen
import com.cosmicknockdown.kingsngoblins.screens.LoadingScreen
import com.cosmicknockdown.kingsngoblins.screens.MenuScreen
import java.util.*
import javax.xml.soap.Text

class KNG() : Game() {
    private val menu: Int = 0
    private val game: Int = 1
    private val loading: Int = 2

    var assetManager: AssetManager = AssetManager()
    val textureAssets = arrayOf("textures/player/player_atlas_orange.png", "textures/menu/menu_bg.png")
    val bitmapFontAssets = arrayOf("fonts/main.fnt")
    val soundAssets = arrayOf("sounds/bg.wav")

    init {
        Texture::class.java
        loadAsset(textureAssets, Texture::class.java)
        loadAsset(bitmapFontAssets, BitmapFont::class.java)
        loadAsset(soundAssets, Music::class.java)
    }

    fun loadAsset(arrayOfAssets: Array<String>, assetClass: Class<*>) {
        arrayOfAssets.forEach {
            assetManager.load(it, assetClass)
        }
    }

    private var state: Int = -1
        set(value) {
            field = value
            if (screen != null)
                screen.dispose()
            when (state) {
                menu -> setScreen(MenuScreen(this))
                game -> setScreen(GameScreen(this))
                loading -> setScreen(LoadingScreen(this))
            }
        }

    fun showLoadingScreen() {
        state = loading
    }

    fun showMenuScreen() {
        state = menu
    }

    fun showGameScreen() {
        state = game
    }

    override fun create() {
        state = loading
    }

    companion object {
        const val PPM: Float = 16f

        const val NOTHING_BIT: Short = 0
        const val WALL_BIT: Short = 1
        const val PLAYER_BIT: Short = 1 shl 1
        const val ENEMY_BIT: Short = 1 shl 2
    }

}