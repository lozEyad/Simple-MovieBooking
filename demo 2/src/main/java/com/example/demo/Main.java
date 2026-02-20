package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        try {
            clearDataFile();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void clearDataFile() {
        try {
            FileWriter writer = new FileWriter("event_data.txt", false);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error clearing data file: " + e.getMessage());
        }
    }

}