package com.java4b.tictactoe;

public class Server {
    public static void main(String[] args) {
        Router router = new Router();
        System.out.println("Starting Server..");
        router.start();
        System.out.println("Server Started..");
        GameControllerClient gcc = new GameControllerClient("localhost", 11111);
        GameMatcherClient gmc = new GameMatcherClient("localhost", 11111);
    }
}
