package com.java4b.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;
import java.lang.Boolean;

public class SinglePlayerGameController {
    @FXML
    private Label name1Label, name2Label, activePlayerLabel;

    @FXML
    private ImageView avatar1ImageView, avatar2ImageView;

    @FXML
    private ImageView square0, square1, square2, square3, square4, square5, square6, square7, square8;

    // Game state attributes; maybe make a separate GameState class later
    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Map<ImageView, Boolean> isFilledMap;        // keeps track of which squares have been played

    // The initialize() method is automatically called after the @FXML fields have been injected. This is where we
    // set up the initial game state.
    @FXML
    public void initialize() {
        player1 = new Player("Player 1", Player.Avatar.ANCHOR);
        player2 = new Player("Computer", Player.Avatar.FLOTATION);

        name1Label.setText(player1.getName() + ":");
        name2Label.setText(player2.getName() + ":");

        avatar1ImageView.setImage(player1.getAvatarImage());
        avatar2ImageView.setImage(player2.getAvatarImage());

        activePlayer = player1;
        setActivePlayerLabel();

        isFilledMap = new HashMap<>(9) {{
            put(square0, Boolean.FALSE);
            put(square1, Boolean.FALSE);
            put(square2, Boolean.FALSE);
            put(square3, Boolean.FALSE);
            put(square4, Boolean.FALSE);
            put(square5, Boolean.FALSE);
            put(square6, Boolean.FALSE);
            put(square7, Boolean.FALSE);
            put(square8, Boolean.FALSE);
        }};
    }

    @FXML
    protected void onSquareClicked(MouseEvent event) {
        // Get the object that the event was triggered on. In this case, it's the ImageView square that was clicked on.
        ImageView selectedSquare = (ImageView)event.getSource();

        // Check to see if that square has already been filled. If not, insert the active player's avatar icon into
        // the square, change the map entry to show it is now filled, and change the active player for the next turn.
        if (!isFilledMap.get(selectedSquare)) {
            selectedSquare.setImage(activePlayer.getAvatarImage());
            isFilledMap.put(selectedSquare, Boolean.TRUE);

            togglePlayer();
        }
    }

    private void togglePlayer() {
        if (activePlayer == player1)
            activePlayer = player2;
        else
            activePlayer = player1;

        setActivePlayerLabel();
    }

    private void setActivePlayerLabel() {
        activePlayerLabel.setText(activePlayer.getName() + "'s turn");
    }
}