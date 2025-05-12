package com.java4b.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {
    @FXML
    protected ToggleButton player1Toggle, player2Toggle;

    @FXML
    protected Button startButton;

    protected GameState gameState;
    protected LocalGameController caller;

    public void initData(LocalGameController caller, GameState gameState) {
        this.gameState = gameState;
        this.caller = caller;
        player1Toggle.setText(gameState.getPlayer1().getName());
        player2Toggle.setText(gameState.getPlayer2().getName());
    }

    @FXML
    protected void onStartButtonClicked() throws IOException {
        Player firstPlayer = (player1Toggle.isSelected() ? gameState.getPlayer1() : gameState.getPlayer2());
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.close();

        caller.startGame(firstPlayer);
    }
}
