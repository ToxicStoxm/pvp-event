package com.x_tornado10.pvpevent;

public class Message {
    private String identifier;
    private String message;
    private PvpEvent plugin = PvpEvent.getInstance();
    private log_level logLevel;
    public Message(String identifier, String message, PvpEvent plugin, log_level logLevel) {
        this.identifier = identifier;
        this.message = message;
        this.plugin = plugin;
        this.logLevel = logLevel;
    }
}
