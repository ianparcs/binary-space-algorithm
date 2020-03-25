package com.sparcsky.summerydays.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sparcsky.summerydays.SummeryDays;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1920;
        config.height = 1080;
        config.forceExit = false;
        config.title = "Summery Days";
        new LwjglApplication(new SummeryDays(), config);
    }
}
