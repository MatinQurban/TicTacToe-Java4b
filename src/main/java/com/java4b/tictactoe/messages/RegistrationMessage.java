package com.java4b.tictactoe.messages;

public class RegistrationMessage extends Message {
    private static final long serialVerisionUID = 1L;
    private final String channelToRegister;

    public RegistrationMessage(String channelToRegister) {
        super("/sys", "REGISTRATION");
        this.channelToRegister = channelToRegister;
    }

    public String getChannelToRegister() {
        return channelToRegister;
    }

    @Override
    public String toString() {
        return super.toString() + "\nChannel to Register: " + channelToRegister;
    }
}
