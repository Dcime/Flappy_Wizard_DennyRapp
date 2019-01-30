package com.dennyrapp.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dennyrapp.game.FlappyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Flappy Wizard";//Name des Spiels
		config.width = 1280;//Breite
		config.height = 720;//Hoehe
		new LwjglApplication(new FlappyGame(), config);
	}
}
