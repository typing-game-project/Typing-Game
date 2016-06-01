package com.brunomarcos.typinggame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brunomarcos.typinggame.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Type Sim";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;
		config.addIcon("img/icon128.jpg", Files.FileType.Internal);
		config.addIcon("img/icon32.png", Files.FileType.Internal);
		config.addIcon("img/icon16.png", Files.FileType.Internal);
		new LwjglApplication(new GameManager(), config);
	}
}
