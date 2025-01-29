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

public class MainMenuController {

    @FXML
    private Button singlePlayer;
    @FXML
    private Button multiPlayerLocal;
    @FXML
    private Button multiPlayerOnline;


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @FXML
    protected void SwitchToGame(String fxml, Stage stage) throws IOException {
        Parent root = loadFXML(fxml);
        Scene gameScene = new Scene(root);
        stage.setScene(gameScene);
    }

    @FXML
    protected void SwitchMenu(ActionEvent event) throws IOException {
        // Get the source of which menu button was clicked
        String menuID = ((Button) event.getSource()).getId();
        Stage stage = (Stage) singlePlayer.getScene().getWindow();

        switch(menuID){
            case "SP":
                SwitchToGame("single-player-game-view", stage);
                break;
            case "LM":
                //SwitchToGame("local-multiplayer-game-view", stage);
                break;
            case "OM":
                //SwitchToGame("online-multiplayer-game-view", stage);
                break;
        }

    }
}
