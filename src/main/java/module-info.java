module com.java4b.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.java4b.tictactoe to javafx.fxml;
    exports com.java4b.tictactoe;
}