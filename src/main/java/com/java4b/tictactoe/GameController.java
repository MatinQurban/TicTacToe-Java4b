package com.java4b.tictactoe;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameController {

    @FXML
    protected Label gameModeLabel, name1Label, name2Label, activePlayerLabel;

    @FXML
    protected ImageView avatar1ImageView, avatar2ImageView;

    @FXML
    protected StackPane cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8;

    GameState gameState;
    ArrayList<StackPane> cells;

    @FXML
    public void initialize() {
        cells = new ArrayList<>(Arrays.asList(cell0, cell1, cell2, cell3,
                cell4, cell5, cell6, cell7, cell8));

        name1Label.setText(gameState.getPlayer1().getName() + ":");
        name2Label.setText(gameState.getPlayer2().getName() + ":");
        avatar1ImageView.setImage(gameState.getPlayer1().getAvatar().getImage());
        avatar2ImageView.setImage(gameState.getPlayer2().getAvatar().getImage());

        setActivePlayerLabel();
    }

    /*
       Might switch to using this initialization function instead if we need the game state to be initialized
       outside the game controller later on.
     */
//    public void initData(GameState gameState) {}

    @FXML
    protected void onCellClicked(MouseEvent event) throws IOException {
        // Get the object that the event was triggered on. In this case, it's the StackPane cell that was clicked on.
        StackPane selectedCell = (StackPane)event.getSource();
        int indexOfSelected = cells.indexOf(selectedCell);

        // Check to see if that cell has already been filled.
        if (gameState.isCellEmpty(indexOfSelected)) {

            // Add the avatar to the cell that was clicked on
            BackgroundSize imageSize = new BackgroundSize(0.70, 0.70, true, true, false, false);
            selectedCell.setBackground(new Background(new BackgroundImage(gameState.getActivePlayer().getAvatar().getImage(),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, imageSize)));

            // Update the game state
            gameState.playCell(indexOfSelected);

            // Check if the game ended (win or draw)
            if (gameState.winOrDraw(getMoveCount())) {
                declareWinner();
            } else {
                gameState.toggleActivePlayer();
                setActivePlayerLabel();
                setCursorAsAvatar();
            }

            selectedCell.getStyleClass().clear(); // Remove hover effect
        } else {
            animateError(selectedCell); // Invalid move feedback
        }
    }

    @FXML
    protected void onMouseOverBoard(MouseEvent event) {
        setCursorAsAvatar();
    }

    @FXML
    protected void onMouseExitBoard(MouseEvent event) {
        Scene scene = activePlayerLabel.getScene();
        scene.setCursor(Cursor.DEFAULT);
    }

    @FXML
    protected void switchToMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) activePlayerLabel.getScene().getWindow();
        TicTacToeApplication.switchScene("MainMenu", stage);
    }

    protected void setCursorAsAvatar() {
        String fileName = gameState.getActivePlayer().getAvatar().getFileName();
        Image image = new Image(getClass().getResource(fileName).toString(), 50, 50, true, false);
        Scene scene = activePlayerLabel.getScene();

        // Change the cursor to the active player's avatar and move the cursor's "hotspot" to the center of the image
        scene.setCursor(new ImageCursor(image, image.getWidth() / 2.0, image.getHeight() / 2.0));
    }

    protected void setActivePlayerLabel() {
        activePlayerLabel.setText(gameState.getActivePlayer().getName() + "'s turn");
    }

    protected int getMoveCount() {
        int count = 0;
        for (StackPane cell : cells) {
            if (cell.getBackground() != null) { // If a cell has an avatar, it's occupied
                count++;
            }
        }
        return count;
    }

    protected void declareWinner() throws IOException {
        String resultMessage;
        Stage stage = (Stage) activePlayerLabel.getScene().getWindow();
        if (gameState.checkRowWin() || gameState.checkColWin() || gameState.checkDiagWin()) {
            resultMessage = gameState.getActivePlayer().getAvatar() == Avatar.ANCHOR ? "Anchor wins!" : "Life-Saver wins!";
        } else {
            // No winner, check if the game is a draw
            resultMessage = "It's a tie!";
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("endScreen.fxml"));
        stage.setScene(new Scene(loader.load()));
        EndGameController controller = loader.getController();
        controller.setCaller(this);
        controller.displayWinnerText(resultMessage);
    }

    private void animateError(StackPane cell) {
        Image errorImage = new Image(getClass().getResource("placement_error.png").toString(),
                cell.getWidth(), cell.getHeight(), true, false);

        ImageView errorImageView = new ImageView(errorImage);
        errorImageView.setOpacity(0.0);

        cell.getChildren().add(errorImageView);

        // Define an animation that will gradually fade out the error image until its no longer visible
        FadeTransition errorFade = new FadeTransition(Duration.millis(800), errorImageView);
        errorFade.setFromValue(1.0);
        errorFade.setToValue(0.0);

        // Define an animation that will shake the board
        GridPane gridPane = (GridPane) cell.getParent();
        TranslateTransition shakeBoard = new TranslateTransition(Duration.millis(50), gridPane);
        shakeBoard.setByX(15);
        shakeBoard.setAutoReverse(true);
        shakeBoard.setCycleCount(10);

        // Disable any inputs to the board (the grid pane and all its child nodes) and hides the cursor. These changes
        // will be reverted after the animation finishes.
        gridPane.setDisable(true);
        activePlayerLabel.getScene().setCursor(Cursor.NONE);

        // Define a lambda expression that will get called after the animation finishes.
        errorFade.setOnFinished(event -> {

            // Removes the error image from the StackPane that represents the cell
            cell.getChildren().removeLast();

            // Re-enables inputs to the board (and its children cells)
            gridPane.setDisable(false);

            // Changes the cursor from hidden to the active player's avatar or the default cursor depending on location.
            if (gridPane.isHover())
                setCursorAsAvatar();
            else
                activePlayerLabel.getScene().setCursor(Cursor.DEFAULT);
        });

        // Start the animations (they run concurrently since they will be on separate threads)
        errorFade.play();
        shakeBoard.play();
    }
}