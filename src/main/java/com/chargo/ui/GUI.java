package com.chargo.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

public class GUI extends Application {

    public static Stage stage = null;

    public GUI() {
    }

    public void launchApp() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GUI.stage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main.fxml")));
        stage.setTitle("Projet M1 - Codage et Crypto");
        stage.getIcons().add(new Image("48857.png"));
        stage.centerOnScreen();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
