package com.java4b.tictactoe;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private final String SERVER_IP;
    private final int SERVER_PORT;

    Client(String Server_IP, int Server_Port) {
        SERVER_IP = Server_IP;
        SERVER_PORT = Server_Port;
        start();
    }

    public void run() {
        String hostname = SERVER_IP;
        int port = SERVER_PORT;

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to server!");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

//            System.out.print("Register your channel name: ");
//            String myChannel = scanner.nextLine();


            // Send initial registration
            output.writeObject(new Message(myChannel, "", "REGISTER"));
            output.flush();

            // Separate thread to listen for incoming messages
            new Thread(() -> {
                try {
                    while (true) {
                        Message message = (Message) input.readObject();
                        System.out.println("\nMessage from " + message.getType() + ": " + message.getText());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread sends messages
            while (true) {
                System.out.print("Send to channel: ");
                String targetChannel = scanner.nextLine();

                System.out.print("Enter text: ");
                String text = scanner.nextLine();

                // type could be something like "CHAT", "MOVE", whatever
                Message message = new Message(targetChannel, text, myChannel);
                output.writeObject(message);
                output.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
