package com.x_tornado10.pvpevent;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PvpEvent extends JavaPlugin {
    public static Logger logger;
    private long start;
    private static PvpEvent instance;
    public final Settings settings = new Settings();

    @Override
    public void onLoad() {
        // Starting timestamp
        start = System.currentTimeMillis();
        logger = getLogger();
        instance = this;
        settings.reload(getConfig());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic



        // calculating and displaying time it took to enable
        long finalTime = System.currentTimeMillis() - start;
        logger.info("Successfully enabled! (took" + (int)finalTime/1000 + "," + finalTime % 1000 + "s)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // End Settings dumpTask, dump manually if not running
        if(!settings.endDumpTask()) settings.dump();
        logger.info("Shutting down. Goodbye!");
    }

    // main instance getter
    public static PvpEvent getInstance() {
        if (instance == null) {
            logger.severe("There was an error while trying to get an instance of the main class. Please restart the server to prevent any unwanted behaviour!");
        }
        return instance;
    }
}
