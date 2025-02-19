package com.java4b.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameController {

    @FXML
    private Label winnerText;
    @FXML
    private Button PA;
    @FXML
    private Button RTM;


    @FXML
    protected void switchMenu(ActionEvent event) throws IOException {

        Stage stage = (Stage) winnerText.getScene().getWindow();

        // Get the source of which menu button was clicked
        String menuID = ((Button) event.getSource()).getId();

        // Based on which menu button was clicked, switch to that corresponding fxml file
        switch(menuID){
            case "PA":
                TicTacToeApplication.switchScene("game-view", stage, null);
                break;
            case "RTM":
                TicTacToeApplication.switchScene("MainMenu", stage, null);
                break;
        }
    }

    @FXML
    void displayWinnerText(String gameWinText)
    {
        winnerText.setText(gameWinText);
    }



}
