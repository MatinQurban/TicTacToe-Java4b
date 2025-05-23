package com.java4b.tictactoe.messages;

import com.java4b.tictactoe.Avatar;

public class GameFoundMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String targetChannel;
    private final String gameChannel;
    private final String opponentName;
    private final Avatar myAvatar;
    private final Avatar opponentAvatar;
    private final String firstPlayer;
    private final boolean isPrivate;

    public GameFoundMessage(String targetChannel, String gameChannel, String opponentName,
                            Avatar myAvatar, Avatar opponentAvatar, String firstPlayer, boolean isPrivate) {
        super(targetChannel, "GAME_FOUND");
        this.targetChannel = targetChannel;
        this.gameChannel = gameChannel;
        this.opponentName = opponentName;
        this.myAvatar = myAvatar;
        this.opponentAvatar = opponentAvatar;
        this.firstPlayer = firstPlayer;
        this.isPrivate = isPrivate;
    }

    public String getTargetChannel() { return targetChannel; }

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

    public boolean isPrivate() { return isPrivate; }

    @Override
    public String toString() {
        return super.toString()
                + "\nTarget channel: " + targetChannel
                + "\nGame channel: " + gameChannel
                + "\nOpponent name: " + opponentName
                + "\nMy avatar: " + myAvatar.toString()
                + "\nOpponent avatar: " + opponentAvatar.toString()
                + "\nFirst player: " + firstPlayer;
    }
}
