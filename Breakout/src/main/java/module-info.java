module org.example.breakout {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.breakout to javafx.fxml;
    exports org.example.breakout;
}