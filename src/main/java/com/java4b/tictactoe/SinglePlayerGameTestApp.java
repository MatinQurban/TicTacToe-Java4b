package com.java4b.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SinglePlayerGameTestApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinglePlayerGameTestApp.class.getResource("single-player-game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TIC TAC TOE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}