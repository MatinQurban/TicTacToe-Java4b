package com.java4b.tictactoe;

import javafx.scene.image.Image;

public enum Avatar {
    ANCHOR, LIFE_SAVER, NONE;

    public String getFileName() {
        return switch (this) {
            case ANCHOR -> "anchor.png";
            case LIFE_SAVER -> "life_saver.png";
            case NONE -> null;
        };
    }

    public Image getImage() {
        return switch (this) {
            case ANCHOR -> new Image(getClass().getResource(ANCHOR.getFileName()).toString());
            case LIFE_SAVER -> new Image(getClass().getResource(LIFE_SAVER.getFileName()).toString());
            case NONE -> null;
        };
    }

    public boolean isEmpty() {
        return this == NONE;
    }
}
