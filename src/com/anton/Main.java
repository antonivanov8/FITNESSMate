package com.anton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root,900,584);
        scene.getStylesheets().add(Main.class.getResource("css/fitnessmatestyle.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setTitle("FITNESSMate 1.0");
        stage.setScene(scene);
        stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }


    public static void main(String[] args) {
        launch(args);
    }
}