package com.java4b.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    protected void SwitchToSinglePlayer() throws IOException {
        Parent root = loadFXML("single-player-game-view");
        Scene singleplayerScene = new Scene(root);
        Stage stage = (Stage) singlePlayer.getScene().getWindow();
        stage.setScene(singleplayerScene);
    }
}
