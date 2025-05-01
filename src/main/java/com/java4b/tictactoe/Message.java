package com.java4b.tictactoe;

import java.io.Serializable;

public class Message implements Serializable {

    // Maybe use an enum for the message type instead of strings
    public enum Type {
        REGISTRATION, JOIN_QUEUE
    }

    protected String channel;
    protected String type;
    private static final long serialVerisionUID = 1L;

    public Message(String channel, String type) {
        this.channel = channel;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "Type: " + type + "\nTo: " + channel;
    }
}
