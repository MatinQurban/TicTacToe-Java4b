package com.java4b.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



import java.io.IOException;

public class MainMenuController{

    @FXML
    private Button singlePlayer;
    @FXML
    private Button multiPlayerLocal;
    @FXML
    private Button multiPlayerOnline;

    @FXML
    protected void switchToGame(String fxml, Stage stage) throws IOException {
        Parent root = TicTacToeApplication.loadFXML(fxml);
        Scene gameScene = new Scene(root);
        stage.setScene(gameScene);
    }

    @FXML
    protected void switchMenu(ActionEvent event) throws IOException {

        Stage stage = (Stage) singlePlayer.getScene().getWindow(); // Can maybe make this a global var?

        // Get the source of which menu button was clicked
        String menuID = ((Button) event.getSource()).getId();

        // Based on which menu button was clicked, switch to that corresponding fxml file
        switch(menuID){
            case "SP":
                switchToGame("single-player-game-view", stage);
                break;
            case "LM":
                //SwitchToGame("local-multiplayer-game-view", stage);
                break;
            case "OM":
                //SwitchToGame("online-multiplayer-game-view", stage);
                break;
        }


        // Current have it so every game mode has different fxml file, however the different game
        // screens will look nearly identical. Only difference is 'vs Computer' or 'vs Player_name'
        // and title. Will keep as seperate for now.
    }
}
