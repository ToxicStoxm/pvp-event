package com.x_tornado10.pvpevent;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MessageHandler {

    private final Logger logger;

    public MessageHandler(Logger logger) {

        this.logger = logger;

    }

    public void send(String message, @Nonnull log_type log_type, @Nonnull log_level log_level) {

        switch (log_level) {
            case NORMAL -> log(message, log_type);
            case DEBUG -> {
                if (PvpEvent.settings.getLogLevel().equals(log_level) || PvpEvent.settings.getLogLevel().equals(com.x_tornado10.pvpevent.log_level.DEBUG_VERBOSE)) log(message,log_type);
            }
            case DEBUG_VERBOSE -> {
                if (PvpEvent.settings.getLogLevel().equals(log_level)) log(message,log_type);
            }

        }

    }

    private void log(String message, log_type log_type) {
        switch (log_type) {
            case INFO -> logger.info(message);
            case WARNING -> logger.warning(message);
            case ERROR -> logger.severe(message);
        }
    }


}
