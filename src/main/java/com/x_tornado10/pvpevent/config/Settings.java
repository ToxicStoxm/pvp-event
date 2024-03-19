package com.x_tornado10.pvpevent.config;

import com.x_tornado10.pvpevent.PvpEvent;
import com.x_tornado10.pvpevent.log.LogHandler;
import com.x_tornado10.pvpevent.log.log_level;
import com.x_tornado10.pvpevent.log.log_type;
import com.x_tornado10.pvpevent.util.Paths;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class Settings {

    private FileConfiguration conf = null;

    private BukkitRunnable dumpTask = null;
    private final PvpEvent plugin;
    private final LogHandler logger;

    public Settings() {
        plugin = PvpEvent.getInstance();
        logger = PvpEvent.getLog();
    }

    // start automatic config saving / dumping process. return false if something fails
    public boolean startDumpTask() {
        logger.send("Starting config dump task", log_type.INFO, log_level.DEBUG);
        if (!(dumpTask == null || !dumpTask.isCancelled())) {
            logger.send("starting dump task failed", log_type.ERROR, log_level.DEBUG_VERBOSE);
            if (dumpTask == null) logger.send("Dump Task could not be initialized. Cause: null", log_type.WARNING, log_level.DEBUG_VERBOSE);
            if (dumpTask.isCancelled()) logger.send("Dump Task could not be initialized. Cause: already cancelled", log_type.WARNING, log_level.DEBUG_VERBOSE);
            return false;
        }

        dumpTask = new BukkitRunnable() {
            @Override
            public void run() {
                logger.send("Saving config values to storage...", log_type.INFO, log_level.DEBUG);
                dump();
            }
        };
        dumpTask.runTaskTimerAsynchronously(plugin, 0, 10 * 60 * 20);
        logger.send("Dump Task was successfully initialized and scheduled to run every 10 minutes.", log_type.INFO, log_level.DEBUG_VERBOSE);
        return true;
    }

    // kill / end automatic saving task
    public boolean endDumpTask() {
        logger.send("Stopping config dump task", log_type.INFO, log_level.DEBUG);
        if (dumpTask == null || !dumpTask.isCancelled()) {
            logger.send("stopping dump task failed", log_type.ERROR, log_level.DEBUG_VERBOSE);
            if (dumpTask == null)
                logger.send("Dump Task could not be stopped. Cause: null", log_type.WARNING, log_level.DEBUG_VERBOSE);
            if (dumpTask.isCancelled())
                logger.send("Dump Task could not be stopped. Cause: already cancelled", log_type.WARNING, log_level.DEBUG_VERBOSE);
            return false;
        }
        dumpTask.cancel();
        logger.send("Dump Task was successfully cancelled.", log_type.INFO, log_level.DEBUG_VERBOSE);
        return true;
    }

    // dump / save config to storage
    public void dump() {
        logger.send("Saving values...", log_type.INFO, log_level.DEBUG_VERBOSE);
        if (plugin.saveConfigValues(conf)) {
            logger.send("Values saved!", log_type.INFO, log_level.DEBUG_VERBOSE);
        } else {
            logger.send("Failed to save config values!", log_type.ERROR, log_level.DEBUG);
        }

    }

    // load / reload config values from the file
    public void reload(FileConfiguration conf) {
        logger.send("Reloading / loading config values from storage", log_type.INFO, log_level.DEBUG_VERBOSE);
        if (conf != null) this.conf = conf;
        else logger.send("Config values failed to load from storage. Please restart the server to prevent further errors!", log_type.ERROR, log_level.NORMAL);
    }

    // Getting Over-world time value
    public int getOverWorldTime() {
        return conf.getInt(Paths.over_world_time);
    }

    // Getting Nether time value
    public int getNetherTime() {
        return conf.getInt(Paths.nether_time);
    }

    // Getting PVP time value
    public int getPvpTime() {
        return conf.getInt(Paths.pvp_time);
    }

    // checking logger basic debug mode
    public boolean isLoggerBasicDebug() {
        return conf.getBoolean(Paths.logger_debug);
    }

    // checking logger core debug mode (verbose)
    public boolean isLoggerCoreDebuggingVerbose() {
        return conf.getBoolean(Paths.logger_debug_core);
    }

    // returns the current log level specified by the user in the config file
    public log_level getLogLevel() {
        return isLoggerCoreDebuggingVerbose() ? log_level.DEBUG_VERBOSE : isLoggerBasicDebug() ? log_level.DEBUG : log_level.NORMAL;
    }

}
