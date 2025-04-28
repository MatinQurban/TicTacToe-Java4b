package com.java4b.tictactoe;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private String channelName;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            // First message from client should contain the channel name
            Message initial = (Message) input.readObject();
            this.channelName = initial.getChannel();
            Server.clients.put(channelName, output);  //adds to hashmap

            System.out.println("Channel registered: " + channelName);

            // Now listen for messages
            while (true) {
                Message message = (Message) input.readObject();
                System.out.println("Received from " + channelName + ": " + message);

                // Forward the message to the correct channel
                ObjectOutputStream targetOutput = Server.clients.get(message.getChannel());
                if (targetOutput != null) {
                    targetOutput.writeObject(message);
                    targetOutput.flush();
                } else {
                    System.out.println("Channel not found: " + message.getChannel());
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected: " + channelName);
            Server.clients.remove(channelName);
        }
    }
}
