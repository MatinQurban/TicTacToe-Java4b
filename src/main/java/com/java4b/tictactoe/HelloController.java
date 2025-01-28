package com.java4b.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private void switchToGameBoard() throws IOException {
        TicTacToeApplication.setRoot("GAME_BOARD_FXML_FILE");
    }
}