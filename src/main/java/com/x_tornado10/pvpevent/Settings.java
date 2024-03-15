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


    public boolean endDumpTask() {
       if (dumpTask == null || !dumpTask.isCancelled()) return false;
       dumpTask.cancel();
       return true;
    }

    public void dump() {


    }

    public void reload(FileConfiguration conf) {
        this.conf = conf;
    }

    public boolean isLoggerBasicDebug() {
        return conf.getBoolean("Debugging.logger-basic-debug");
    }

    public void setLoggerBasicDebug(boolean value) {
        conf.set("Debugging.logger-basic-debug", value);
    }

    public boolean isLoggerCoreDebuggingVerbose() {
        return conf.getBoolean("Debugging.logger-core-debugging-verbose");
    }

    public void setLoggerCoreDebuggingVerbose(boolean value) {
        conf.set("Debugging.logger-core-debugging-verbose", value);
    }

    public log_level getLogLevel() {
        return isLoggerCoreDebuggingVerbose() ? log_level.DEBUG_VERBOSE : isLoggerBasicDebug() ? log_level.DEBUG : log_level.NORMAL;
    }

}
