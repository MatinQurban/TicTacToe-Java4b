package com.java4b.tictactoe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Client is an abstract class with a pure virtual method
public abstract class Client {
//    protected static final String SERVER_IP = "localhost";
//    protected static final int SERVER_PORT = 11111;
    protected String SERVER_IP;
    protected int SERVER_PORT;

    protected Socket socket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    public Client() {}

    public Client(String serverIP, int serverPort) {
        this.SERVER_IP = serverIP;
        this.SERVER_PORT = serverPort;
        connectToRouter(serverIP, serverPort);

//        try {
//            socket = new Socket(SERVER_IP, SERVER_PORT);
//            out = new ObjectOutputStream(this.socket.getOutputStream());
//            in = new ObjectInputStream(this.socket.getInputStream());
//
//            new Thread(this::listenForMessages).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    protected void connectToRouter(String serverIP, int serverPort) {
        try {
            this.SERVER_IP = serverIP;
            this.SERVER_PORT = serverPort;

            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new ObjectOutputStream(this.socket.getOutputStream());
            in = new ObjectInputStream(this.socket.getInputStream());

            new Thread(this::listenForMessages).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to send message to clientConnection");
        }
    }

    // Pure virtual method
    protected abstract void listenForMessages();
}