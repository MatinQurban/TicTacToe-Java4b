package com.java4b.tictactoe;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    // Channel name ➔ OutputStream to talk to that client
    static ConcurrentHashMap<String, ObjectOutputStream> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = 12345; // port number

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
