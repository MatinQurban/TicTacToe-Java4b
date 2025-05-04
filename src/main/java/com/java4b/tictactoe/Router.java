package com.java4b.tictactoe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Router {
    private static final int PORT = 11111;
    private Map<String, List<ClientConnection>> clientsByChannel = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Router router = new Router();
        router.start();
    }

    public void broadcast(Message message) {
        List<ClientConnection> receivers = clientsByChannel.get(message.getChannel());
        for (ClientConnection client : receivers) {
            client.sendMessage(message);
        }
    }

    public void registerClient(String channel, ClientConnection connection) {
        clientsByChannel.putIfAbsent(channel, new ArrayList<>());
        clientsByChannel.get(channel).add(connection);

        System.out.println("New clientConnection registered for channel:  " + channel + "\n" + clientsByChannel.get(channel));
    }

    public void unRegisterClient(String channel, ClientConnection connection) {
        clientsByChannel.get(channel).remove(connection);
        System.out.println("clientConnection unregistered from channel:  " + channel + "\n" + clientsByChannel.get(channel));
    }

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on " + PORT);
            System.out.println("Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(clientSocket, this);
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
