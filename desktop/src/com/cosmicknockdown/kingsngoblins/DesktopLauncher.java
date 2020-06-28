package com.cosmicknockdown.kingsngoblins;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(final String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 16 * 48;
        config.height = 16 * 43;
        new LwjglApplication(new KNG(), config);
    }
}
