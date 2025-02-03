package com.java4b.tictactoe;

import javafx.scene.image.Image;

public class Player {

    private String name;
    private Avatar avatar;

    public Player(String name, Avatar avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public Avatar getAvatar() {
        return avatar;
    }
}
