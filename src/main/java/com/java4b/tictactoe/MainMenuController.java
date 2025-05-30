package com.java4b.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    protected void switchMenu(ActionEvent event) throws IOException {

        Stage stage = (Stage) singlePlayer.getScene().getWindow(); // Can maybe make this a global var?

        // Get the source of which menu button was clicked
        String menuID = ((Button) event.getSource()).getId();

        GameState gameState;

        // Based on which menu button was clicked, switch to that corresponding fxml file
        switch(menuID){
            case "SP":
                loadGame(stage, new SinglePlayerGameController());
                break;
            case "LM":
                loadGame(stage, new TwoPlayerGameController());
                break;
            case "OM":
                // Call OnlineMPGameController;
                //TicTacToeApplication.loadGame("online-multiplayer-game-view", stage);
                FXMLLoader loader = new FXMLLoader(TicTacToeApplication.class.getResource("background-view.fxml"));
                stage.setScene(new Scene(loader.load()));
                new PlayerClient(stage);
                break;
        }
    }

    public static void loadGame(Stage stage, GameController controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(TicTacToeApplication.class.getResource("game-view.fxml"));
        loader.setController(controller);
        stage.setScene(new Scene(loader.load()));
    }
}
