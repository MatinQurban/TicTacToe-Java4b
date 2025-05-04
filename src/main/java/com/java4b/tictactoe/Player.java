package com.java4b.tictactoe;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Player implements Serializable {

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
