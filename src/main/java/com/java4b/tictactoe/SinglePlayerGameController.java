package com.java4b.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class SinglePlayerGameController {
    @FXML
    private Label activePlayerLabel;

    @FXML
    private ImageView cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8;

    GameState gameState;
    ArrayList<ImageView> cells;

    // The initialize() method is automatically called after the @FXML fields have been injected
    @FXML
    public void initialize() {

        gameState = new GameState("Player 1", Avatar.ANCHOR, "Computer", Avatar.LIFE_SAVER);
        cells = new ArrayList<>(Arrays.asList(cell0, cell1, cell2, cell3,
                cell4, cell5, cell6, cell7, cell8));

        setActivePlayerLabel();
    }

    @FXML
    protected void onSquareClicked(MouseEvent event) {
        // Get the object that the event was triggered on. In this case, it's the ImageView square that was clicked on.
        ImageView selectedCell = (ImageView)event.getSource();
        int indexOfSelected = cells.indexOf(selectedCell);

        // Check to see if that square has already been filled. If not, insert the active player's avatar icon into
        // the square, change the map entry to show it is now filled, and change the active player for the next turn.
        if (gameState.isCellEmpty(indexOfSelected)) {
            selectedCell.setImage(toImage(gameState.getActivePlayer().getAvatar()));
            gameState.playCell(indexOfSelected);

            gameState.toggleActivePlayer();
            setActivePlayerLabel();
        }
    }

    private Image toImage(Avatar avatar) {
        String fileName = switch (avatar) {
            case Avatar.ANCHOR -> "anchor.png";
            case Avatar.LIFE_SAVER -> "life_saver.png";
            case Avatar.NONE -> null;
        };

        if (fileName != null)
            return new Image(getClass().getResource(fileName).toString());
        else
            return null;
    }

    private void setActivePlayerLabel() {
        activePlayerLabel.setText(gameState.getActivePlayer().getName() + "'s turn");
    }
}