package com.java4b.tictactoe;
import java.net.Socket;
// This class is called from the MainMenuController when the user clicks on Online Multiplayer Option
public class OnlineMPGameController {
    private static final String SERVER_IP = "localhost"; // or the IP address where your Router is running
    private static final int SERVER_PORT = 12345;

//    PlayerClient playerclient = new PlayerClient();

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