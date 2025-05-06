package com.java4b.tictactoe.messages;

public class LoginSuccessfulMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String gamerTag;

    public LoginSuccessfulMessage(String gamerTag) {
        super("/login", "LOGIN_SUCCESSFUL");
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
