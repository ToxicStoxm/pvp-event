package com.x_tornado10.pvpevent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class PvpEvent extends JavaPlugin {
    private static LogHandler logger;
    private long start;
    private static PvpEvent instance;
    public static Settings settings = new Settings();

    @Override
    public void onLoad() {
        // Starting timestamp
        start = System.currentTimeMillis();

        // initialize plugin logger and message handler
        Logger logger = getLogger();
        PvpEvent.logger = new LogHandler(logger);

        instance = this;
        // save the default config if it does not exist
        saveDefaultConfig();
        // load config values into memory
        settings.reload(getConfig());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        // start config dumping task. display error message if it fails
        if (!settings.startDumpTask()) {
            logger.send("Error with dump-task for config.yml! Please restart the server!", log_type.ERROR, log_level.NORMAL);
        }

        // calculating and displaying time it took to enable
        long finalTime = System.currentTimeMillis() - start;
        logger.send("Successfully enabled! (took" + (int)finalTime/1000 + "," + finalTime % 1000 + "s)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // End Settings dumpTask, dump manually if not running
        if(!settings.endDumpTask()) settings.dump();
        logger.send("Shutting down. Goodbye!");
    }

    // main instance getter
    public static PvpEvent getInstance() {
        if (instance == null) {
            logger.send("There was an error while trying to get an instance of the main class. Please restart the server to prevent any unwanted behaviour!", log_type.ERROR, log_level.NORMAL);
        }
        return instance;
    }

    // main getter for the plugin logger
    public static LogHandler getLog() {
        return logger;
    }
    public boolean saveConfigValues(FileConfiguration conf) {
        try {
            conf.save(new File(getDataFolder() + "/config.yml"));
        } catch (IOException e) {return false;}
        return true;
    }
}
