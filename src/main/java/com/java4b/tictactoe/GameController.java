package com.java4b.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GameController {

    protected static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @FXML
    protected void returnToMenu(ActionEvent event) throws IOException {


        Parent mainMenu = loadFXML("MainMenu.fxml");
        Scene scene = new Scene(mainMenu);

        TicTacToeApplication.ticTacToeStage.setScene(scene);
    }

}


