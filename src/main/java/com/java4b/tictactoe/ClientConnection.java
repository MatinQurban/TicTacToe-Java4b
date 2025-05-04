package com.java4b.tictactoe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientConnection extends Thread implements Serializable {
    private final Router router;
    private final Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientConnection(Socket socket, Router router) {
        this.client = socket;
        this.router = router;
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to send message to client");
        }
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            while (client.isConnected()) {
                Message message = (Message) in.readObject();

                if (message.getType().equals("REGISTRATION")) {
                    router.registerClient(((RegistrationMessage) message).getChannelToRegister(), this);
                } else if (message.getType().equals("UNREGISTER")) {
                    router.unRegisterClient(((UnregisterMessage) message).getChannelToUnregisterFrom(), this);
                } else {
                    router.broadcast(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Client has disconnected");
        } finally {
            close();
        }
    }

    private void close() {
        try {
            if (client != null) {
                client.close();
            }

            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
