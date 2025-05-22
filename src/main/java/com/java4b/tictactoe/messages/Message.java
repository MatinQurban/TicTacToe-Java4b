package com.java4b.tictactoe.messages;

import java.io.Serializable;

public class Message implements Serializable {
    protected String channel;
    protected String type;
    private static final long serialVersionUID = 1L;

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
