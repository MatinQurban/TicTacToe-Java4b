module com.java4b.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.java4b.tictactoe to javafx.fxml;
    exports com.java4b.tictactoe;
    exports com.java4b.tictactoe.messages;
    opens com.java4b.tictactoe.messages to javafx.fxml;
//    exports com.java4b.tictactoe.server;
//    opens com.java4b.tictactoe.server to javafx.fxml;
}