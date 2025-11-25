package com.digitalwardrobe.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Digital Wardrobe App");
        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Digital Wardrobe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
