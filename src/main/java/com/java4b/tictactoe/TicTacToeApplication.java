package com.java4b.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToeApplication extends Application {
    private static Scene scene;

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @FXML
    public static void switchScene(String fxml, Stage stage) throws IOException {
        Parent root = loadFXML(fxml);
        Scene gameScene = new Scene(root);
        stage.setScene(gameScene);
    }

    @Override
    public void start(Stage stage) throws IOException {
        switchScene("MainMenu", stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}