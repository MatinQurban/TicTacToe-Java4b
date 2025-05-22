package com.java4b.tictactoe.messages;

public class UnregisterMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String channelToUnregisterFrom;

    public UnregisterMessage(String channelToUnregisterFrom) {
        super("/sys", "UNREGISTER");
        this.channelToUnregisterFrom = channelToUnregisterFrom;
    }

    public String getChannelToUnregisterFrom() {
        return channelToUnregisterFrom;
    }

    @Override
    public String toString() {
        return super.toString()
                +"\nChannel to Unregister from: " +channelToUnregisterFrom;
    }
}
