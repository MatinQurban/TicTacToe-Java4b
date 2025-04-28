package com.java4b.tictactoe;
import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String type;
    private final String channel; // Channel to which the message is sent
    private final String text;

    // Main constructor
    public Message(String channel, String text, String type) {
        this.channel = channel;
        this.text = text;
        this.type = type;
    }

    // Simpler constructor that defaults type to "Message"
    public Message(String channel, String text) {
        this(channel, text, "Message");
    }

    public String getType() {
        return type;
    }

    public String getChannel() {
        return channel;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "To: " + channel + ": " + text;
    }
}
