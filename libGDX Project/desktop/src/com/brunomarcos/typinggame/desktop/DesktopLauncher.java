package com.brunomarcos.typinggame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brunomarcos.typinggame.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Typing Game";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;
		new LwjglApplication(new GameManager(), config);
	}
}
