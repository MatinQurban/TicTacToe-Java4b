package com.java4b.tictactoe;

public class GameManager extends Client {
    // This class should implement a listener method which handles all game-type messages
    // It will also be 'the brains' of the players' games, meaning it will have methods that will
    // manage the game logic and keep a running tab of every game in our server (later)

    public GameManager(String Server_IP, int Server_Port) {
        super(Server_IP, Server_Port);
    }

    // This listener should handle all game-type messages
    @Override
    public void listener() {}
}
