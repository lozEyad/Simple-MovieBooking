package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SignC {

    @FXML
    private PasswordField passwordEntered;

    @FXML
    private Label popUpLabel;

    @FXML
    private TextField usernameField;

    private List<User> users = UserManager.getInstance().getUserList();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void BacklToLog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SignUp(ActionEvent event) {
        if (usernameField.getText().isEmpty() && passwordEntered.getText().isEmpty()) {
            popUpLabel.setText("Please enter a name and a password.");
        } else {
            if (users.isEmpty()) {
                if (usernameField.getText().isEmpty() || passwordEntered.getText().isEmpty()) {
                    popUpLabel.setText("Please enter a name and a password.");
                } else {
                    User newUser = new User(usernameField.getText(), passwordEntered.getText());
                    popUpLabel.setText("User successfully signed up. You can go to the sign in page now!");
                    UserManager.getInstance().addUser(newUser);
                }
            } else {
                boolean userExists = false;
                for (User user : users) {
                    if (usernameField.getText().equals(user.getUsername())) {
                        userExists = true;
                        break;
                    }
                }
                if (userExists) {
                    popUpLabel.setText("User already exists! Please try again.");
                } else {
                    User newUser = new User(usernameField.getText(), passwordEntered.getText());
                    popUpLabel.setText("User successfully signed up. You can go to the sign in page now!");
                    UserManager.getInstance().addUser(newUser);
                }
            }
        }
    }
}
