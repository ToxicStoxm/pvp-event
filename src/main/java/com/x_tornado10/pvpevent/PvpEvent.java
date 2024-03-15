package com.x_tornado10.pvpevent;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PvpEvent extends JavaPlugin {
    private static MessageHandler logger;
    private long start;
    private static PvpEvent instance;
    public static Settings settings = new Settings();

    @Override
    public void onLoad() {
        // Starting timestamp
        start = System.currentTimeMillis();
        Logger logger = getLogger();
        PvpEvent.logger = new MessageHandler(logger);
        instance = this;
        saveDefaultConfig();
        settings.reload(getConfig());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic


        // calculating and displaying time it took to enable
        long finalTime = System.currentTimeMillis() - start;
        logger.send("Successfully enabled! (took" + (int)finalTime/1000 + "," + finalTime % 1000 + "s)", log_type.INFO, log_level.NORMAL);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // End Settings dumpTask, dump manually if not running
        if(!settings.endDumpTask()) settings.dump();
        logger.send("Shutting down. Goodbye!", log_type.INFO, log_level.NORMAL);
    }

    // main instance getter
    public static PvpEvent getInstance() {
        if (instance == null) {
            logger.send("There was an error while trying to get an instance of the main class. Please restart the server to prevent any unwanted behaviour!", log_type.ERROR, log_level.NORMAL);
        }
        return instance;
    }

    public static MessageHandler getLog() {
        return logger;
    }
}
