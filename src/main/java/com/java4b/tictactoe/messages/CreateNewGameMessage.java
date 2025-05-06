package com.java4b.tictactoe.messages;

public class CreateNewGameMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String player1Name;
    private final String player2Name;

    public CreateNewGameMessage(String player1Name, String player2Name) {
        super("/create-new-game", "CREATE_NEW_GAME");
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPlayer1 Name: " + player1Name + "\nPlayer2 Name: " + player2Name;
    }
}
