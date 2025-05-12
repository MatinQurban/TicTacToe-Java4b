package com.java4b.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    private VBox vbox;

    private GameController caller;

    public void initData(GameController caller, String gameWinText) {
        this.caller = caller;
        winnerText.setText(gameWinText);

        if (caller instanceof OnlineMPGameController) {
            vbox.getChildren().remove(PA);
        }
    }

    @FXML
    protected void switchMenu(ActionEvent event) throws IOException {

        Stage callerStage = caller.getStage();

        // Get the source of which menu button was clicked
        String menuID = ((Button) event.getSource()).getId();

        // Based on which menu button was clicked, switch to that corresponding fxml file
        switch(menuID){
            case "PA":
                if (caller instanceof SinglePlayerGameController) {
                    MainMenuController.loadGame(callerStage, new SinglePlayerGameController());
                } else if (caller instanceof TwoPlayerGameController) {
                    MainMenuController.loadGame(callerStage, new TwoPlayerGameController());
                }

                break;
            case "RTM":
                TicTacToeApplication.switchScene("MainMenu", callerStage);
                break;
        }

        ((Stage) winnerText.getScene().getWindow()).close();
    }
}