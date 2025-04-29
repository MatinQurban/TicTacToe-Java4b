package com.java4b.tictactoe;
import java.net.Socket;

public class OnlineMPGameController extends GameController {
    private static final String SERVER_IP = "localhost"; // or the IP address where your Router is running
    private static final int SERVER_PORT = 12345;

    @Override
    public void initialize() {
        //gameState = new GameState("Player 1", Avatar.ANCHOR, "Player 2", Avatar.LIFE_SAVER);
        super.initialize(); // TODO: functionality for one game in server architecture
        gameModeLabel.setText("Two Player Game");
        Client playerClient = new Client(SERVER_IP, SERVER_PORT);
        playerClient.start();


    }
}

//Runnable playerClient = () -> {
//            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)
//            {
//                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//                register('game1');
//
//                Thread listener = new Thread(() -> {
//                    try {
//                        Object message;
//                        while ((message = in.readObject()) != null) { // Handle messages
//                            System.out.println("[Server]: " + message); // change this part
//                             switch (check type of message)
//                              {
//                                  do with message
//                              }
//                        }
//                    } catch (IOException | ClassNotFoundException e) {
//                        System.out.println("Disconnected from server.");
//                    }
//                });
//                listener.start();
//            }
//        }
//        playerClient.start();