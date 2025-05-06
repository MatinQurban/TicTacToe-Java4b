package com.java4b.tictactoe.messages;

public class AttemptLoginMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String gamerTag;

    public AttemptLoginMessage(String gamerTag) {
        super("/login", "ATTEMPT_LOGIN");
        this.gamerTag = gamerTag;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPlayer Name: " + gamerTag;
    }
}
