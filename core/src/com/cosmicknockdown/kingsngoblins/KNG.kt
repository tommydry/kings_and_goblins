package com.cosmicknockdown.kingsngoblins

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.IntMap
import com.cosmicknockdown.kingsngoblins.components.StateComponent
import com.cosmicknockdown.kingsngoblins.screens.GameScreen
import com.cosmicknockdown.kingsngoblins.screens.LoadingScreen
import com.cosmicknockdown.kingsngoblins.screens.MenuScreen
import javax.swing.plaf.nimbus.State

class KNG : Game() {
    private val menu: Int = 0
    private val game: Int = 1
    private val loading: Int = 2

    var assetManager: AssetManager = AssetManager()
    private val textureAssets = arrayOf(PLAYER_ATLAS_PATH, MENU_BG_PATH, TEMP_ATLAS_PATH)
    private val bitmapFontAssets = arrayOf(FONTS_PATH)
    private val soundAssets = arrayOf(BG_SOUND_PATH)

    init {
        Texture::class.java
        loadAsset(textureAssets, Texture::class.java)
        loadAsset(bitmapFontAssets, BitmapFont::class.java)
        loadAsset(soundAssets, Music::class.java)
        loadAsset(arrayOf("uiskin.json"), Skin::class.java)
    }

    private fun loadAsset(arrayOfAssets: Array<String>, assetClass: Class<*>) {
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
//                game -> setScreen(TestScreen(this))
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

    fun getMainCharacterAnimation(): IntMap<Animation<TextureRegion>> {
        return IntMap<Animation<TextureRegion>>().apply {
            val playerTexture = assetManager.get(PLAYER_ATLAS_PATH, Texture::class.java)
            val split = TextureRegion.split(playerTexture, playerTexture.width / 7, playerTexture.height / 4)
            val idleAnimation = Animation<TextureRegion>(0.075f, com.badlogic.gdx.utils.Array(arrayOf(split[1][0], split[1][1], split[1][2], split[1][3])))
            val movementArray = com.badlogic.gdx.utils.Array(arrayOf(split[2][0], split[2][1], split[2][2], split[2][3]))
            val movementAnimation = Animation<TextureRegion>(0.075f, movementArray)

            put(StateComponent.IDLE, idleAnimation)
            put(StateComponent.MOVE_RIGHT, movementAnimation)
            put(StateComponent.MOVE_LEFT, movementAnimation)
            put(StateComponent.RIGHT, idleAnimation)
            put(StateComponent.LEFT, idleAnimation)
        }
    }

    fun getTreasureTexture(): TextureRegion {
        val tempTexture = assetManager.get(TEMP_ATLAS_PATH, Texture::class.java)
        val split = TextureRegion.split(tempTexture, tempTexture.width / 8, tempTexture.height / 12)
        val textureRegion = split[6][0]
        return textureRegion
    }

    companion object {
        const val PPM: Float = 16f

        const val GAME_WIDTH_IN_TILES = 45f
        const val GAME_HEIGHT_IN_TILES = 43f

        const val NOTHING_BIT: Short = 0
        const val WALL_BIT: Short = 1
        const val PLAYER_BIT: Short = 1 shl 1
        const val ENEMY_BIT: Short = 1 shl 2
        const val STUF_BIT: Short = 1 shl 3

        const val PLAYER_ATLAS_PATH = "textures/player/player_atlas_orange.png"
        const val TEMP_ATLAS_PATH = "textures/temp/doss.png"
        const val MENU_BG_PATH = "textures/menu/menu_bg.png"
        const val FONTS_PATH = "fonts/main.fnt"
        const val BG_SOUND_PATH = "sounds/bg.wav"

        const val FIRST_LEVEL_MAP_PATH = "maps/dungeon.tmx"
        const val FIRST_LEVEL_CHARACTER_SPOT_LAYER_NAME = "character_spot"
        const val FIRST_LEVEL_POI_LAYER_NAME = "test_poi"

    }

}