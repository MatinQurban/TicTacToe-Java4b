package com.cs4b.tttprototyping;

import javafx.scene.image.Image;

public class Player {
    public enum Avatar {
        ANCHOR, FLOTATION
    };

    public String toString(Avatar avatar) {
        return switch (avatar) {
            case ANCHOR -> "anchor.png";
            case FLOTATION -> "flotation.png";
        };
    }

    private String name;
    private Image avatarImage;

    public Player(String name, Avatar avatar) {
        this.name = name;
        this.avatarImage = new Image(getClass().getResource(toString(avatar)).toString());
    }

    public String getName() {
        return name;
    }

    public Image getAvatarImage() {
        return avatarImage;
    }
}
