package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Space Invaders");
		String filePath = "C:\\Users\\wardusv3\\Desktop\\BANK\\JAAA\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
		BufferedReader bf = new BufferedReader(filePath);
		if (!bf.optionList.isEmpty()) {
			int firstOption = bf.optionList.get(0);
			int secondOption = bf.optionList.get(1);
			config.setWindowedMode(firstOption, secondOption);
		}
		config.setResizable(false);

		new Lwjgl3Application(new Main(), config);
	}
}