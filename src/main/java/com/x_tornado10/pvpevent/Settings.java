package com.x_tornado10.pvpevent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class Settings {

    private FileConfiguration conf = null;

    private BukkitRunnable dumpTask = null;
    private final PvpEvent plugin;

    public Settings() {
        plugin = PvpEvent.getInstance();
    }

    // start automatic config saving / dumping process. return false if something fails
    public boolean startDumpTask() {
        if (!(dumpTask == null || !dumpTask.isCancelled())) return false;

        dumpTask = new BukkitRunnable() {
            @Override
            public void run() {
                dump();
            }
        };
        dumpTask.runTaskTimerAsynchronously(plugin, 0, 10 * 60 * 20);
        return true;
    }

    // kill / end automatic saving task
    public boolean endDumpTask() {
       if (dumpTask == null || !dumpTask.isCancelled()) return false;
       dumpTask.cancel();
       return true;
    }

    // dump / save config to storage
    public void dump() {


    }

    // load / reload config values from the file
    public void reload(FileConfiguration conf) {
        this.conf = conf;
    }

    // checking / setting logger basic debug mode
    public boolean isLoggerBasicDebug() {
        return conf.getBoolean("Debugging.logger-basic-debug");
    }
    public void setLoggerBasicDebug(boolean value) {
        conf.set("Debugging.logger-basic-debug", value);
    }

    // checking / setting logger core debug mode (verbose)
    public boolean isLoggerCoreDebuggingVerbose() {
        return conf.getBoolean("Debugging.logger-core-debugging-verbose");
    }
    public void setLoggerCoreDebuggingVerbose(boolean value) {
        conf.set("Debugging.logger-core-debugging-verbose", value);
    }

    // returns the current log level specified by the user in the config file
    public log_level getLogLevel() {
        return isLoggerCoreDebuggingVerbose() ? log_level.DEBUG_VERBOSE : isLoggerBasicDebug() ? log_level.DEBUG : log_level.NORMAL;
    }

}
