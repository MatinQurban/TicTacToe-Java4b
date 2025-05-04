package com.java4b.tictactoe;

public class GameFoundMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String gameChannel;
    private final String opponentName;
    private final Avatar myAvatar;
    private final Avatar opponentAvatar;
    private final String firstPlayer;

    public GameFoundMessage(String targetChannel, String gameChannel, String opponentName,
                            Avatar myAvatar, Avatar opponentAvatar, String firstPlayer) {
        super(targetChannel, "GAME_FOUND");
        this.gameChannel = gameChannel;
        this.opponentName = opponentName;
        this.myAvatar = myAvatar;
        this.opponentAvatar = opponentAvatar;
        this.firstPlayer = firstPlayer;
    }

    public String getGameChannel() {
        return gameChannel;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public Avatar getMyAvatar() {
        return myAvatar;
    }

    public Avatar getOpponentAvatar() {
        return opponentAvatar;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nGame channel: " + gameChannel
                + "\nOpponent name: " + opponentName
                + "\nMy avatar: " + myAvatar.toString()
                + "\nOpponent avatar: " + opponentAvatar.toString()
                + "\nFirst player: " + firstPlayer;
    }
}
