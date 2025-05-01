package com.java4b.tictactoe;

public class GameFoundMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String playerName;
    private final String gameChannel;
    private final Avatar avatar;

    public GameFoundMessage(String playerName, Avatar avatar, String gameChannel) {
        super("/lobby", "GAME_FOUND");
        this.playerName = playerName;
        this.gameChannel = gameChannel;
        this.avatar = avatar;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGameChannel() {
        return gameChannel;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nPlayer Name: " + playerName
                + "\nGame Channel: " + gameChannel
                + "\nAvatar: " + avatar.toString();
    }
}
