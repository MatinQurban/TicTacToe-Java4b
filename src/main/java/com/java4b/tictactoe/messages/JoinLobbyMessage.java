package com.java4b.tictactoe.messages;

public class JoinLobbyMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String lobbySubChannel, gameName, gamePassword;

    public JoinLobbyMessage(String lobbySubChannel, String gameName, String gamePassword) {
        super(lobbySubChannel, "JOIN_LOBBY");
        this.lobbySubChannel = lobbySubChannel;
        this.gameName = gameName;
        this.gamePassword = gamePassword;
    }

    public String getLobbySubChannel() {
        return lobbySubChannel;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGamePassword() {
        return gamePassword; // if we wanted to be fancy we could encrypt this
    }

    @Override
    public String toString() {
        return super.toString() + "JoinLobbyMessage{" +
                "lobbySubChannel='" + lobbySubChannel + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gamePassword='" + gamePassword + '\'' +
                '}';
    }
}
