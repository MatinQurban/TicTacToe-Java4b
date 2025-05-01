package com.java4b.tictactoe;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    // Channel name ➔ OutputStream to talk to that client
    static ConcurrentHashMap<String, ObjectOutputStream> clients = new ConcurrentHashMap<>();

    // On main, we should create a router, game manager, (and maybe later: playerLobby)
    public static void main(String[] args) {
        int port = 12345; // port number

        Router router = new Router(port);
        GameManager gameManager = new GameManager("hostname", port);
    }
}
