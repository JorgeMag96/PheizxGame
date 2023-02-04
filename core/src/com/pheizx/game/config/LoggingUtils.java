package com.pheizx.game.config;

import com.badlogic.gdx.Gdx;

public class LoggingUtils {

    public static void logSystemInfo() {
        Gdx.app.log("system_info", "Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        Gdx.app.log("system_info", "Architecture: " + System.getProperty("os.arch"));
        Gdx.app.log("system_info", "Available processor cores: " + Runtime.getRuntime().availableProcessors());
        Gdx.app.log("system_info", "Free memory: " + String.format("%.3f", (Runtime.getRuntime().freeMemory() / 1000000000f)) + "GB");
        Gdx.app.log("system_info", "Total memory: " + String.format("%.3f", (Runtime.getRuntime().totalMemory() / 1000000000f)) + "GB");
        Gdx.app.log("system_info", "Max memory: " + String.format("%.3f", (Runtime.getRuntime().maxMemory() / 1000000000f)) + "GB");
    }
}
