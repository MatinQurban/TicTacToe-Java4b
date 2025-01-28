package com.java4b.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.List;

public class VsComputerController {
    @FXML
    Label playersTurnLabel;

    private List<String> buttonIDs = Arrays.asList("button0", "button1", "button2", "button3", "button4",
            "button5", "button6", "button7", "button8");

    private boolean[] isOccupied = new boolean[9];

    private Image avatar1Image = new Image(getClass().getResource("anchor.png").toString());
    private Image avatar2Image = new Image(getClass().getResource("flotation.png").toString());
    private Image currentAvatar = avatar1Image;

    private String player1Name = "Player 1";
    private String player2Name = "Computer";
    private String currentPlayerName = player1Name;

    @FXML
    public void initialize() {
        playersTurnLabel.setText(currentPlayerName + "'s turn");
    }

    @FXML
    protected void onSquareClicked(MouseEvent event) {
        Button selectedButton = (Button)event.getSource();
        String callerID = ((Button) event.getSource()).getId();
        System.out.println(callerID);
        int buttonIndex = buttonIDs.indexOf(callerID);

        if (!isOccupied[buttonIndex]) {
            ImageView avatarImageView = new ImageView(currentAvatar);
            avatarImageView.setFitHeight(selectedButton.getHeight());
            avatarImageView.setFitWidth(selectedButton.getWidth());
            selectedButton.setGraphic(avatarImageView);
            isOccupied[buttonIndex] = true;

            if (currentPlayerName.equals(player1Name)) {
                currentPlayerName = player2Name;
                currentAvatar = avatar2Image;
            }
            else {
                currentPlayerName = player1Name;
                currentAvatar = avatar1Image;
            }

            playersTurnLabel.setText(currentPlayerName + "'s turn");
        }
    }
}