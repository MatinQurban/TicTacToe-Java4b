package com.java4b.tictactoe.messages;

public class CreateLobbyMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String lobbySubChannel, gameName, gamePassword;

    public CreateLobbyMessage(String lobbySubChannel, String gameName, String gamePassword) {
        super(lobbySubChannel, "CREATE_LOBBY");
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
        return "CreateLobbyMessage{" +
                "lobbySubChannel='" + lobbySubChannel + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gamePassword='" + gamePassword + '\'' +
                '}';
    }
}
