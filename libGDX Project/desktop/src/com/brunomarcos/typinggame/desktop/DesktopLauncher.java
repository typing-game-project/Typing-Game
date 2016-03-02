package com.brunomarcos.typinggame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brunomarcos.typinggame.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Typing Game";
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new GameManager(), config);
	}
}
