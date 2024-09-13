package org.example.breakout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BreakoutGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Create a GameCanvas with specific dimensions
        GameCanvas canvas = new GameCanvas(800, 600);

        // Load the level
        canvas.loadLevel();

        // Add the GameCanvas to a StackPane
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Create a scene and set it to the primary stage
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Breakout Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}