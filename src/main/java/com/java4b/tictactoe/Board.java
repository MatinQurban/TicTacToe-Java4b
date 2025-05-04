package com.java4b.tictactoe;

import java.io.Serializable;
import java.util.Arrays;

public class Board implements Serializable {
    private Avatar[] cells;

    public Board() {
        cells = new Avatar[9];
        Arrays.fill(cells, Avatar.NONE);
    }

    public Avatar getCell(int index) {
        return cells[index];
    }

    public void setCell(int index, Avatar avatar) {
        cells[index] = avatar;
    }
}
