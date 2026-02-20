package com.example.demo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class LogC {

    private List<User> users=UserManager.getInstance().getUserList();
    private Stage stage;
    private Scene scene;
   private User currentUser;



    private Parent root;
    @FXML
    private TextField usernameEntered;
    @FXML
    private PasswordField passwordEntered;
    @FXML
    private Label wrongLabel;
    @FXML
    public void OpenAdminP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void OpenSign(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpDeskA.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void UserLogin(ActionEvent event) throws IOException {
        checkLogin();

    }


    public void checkLogin() throws IOException{
        if(users.isEmpty()){

            wrongLabel.setText("wrong username or password");

        }
        else {
            for (User user : users) {

                if (usernameEntered.getText().toString().equals(user.getUsername()) && passwordEntered.getText().toString().equals(user.getPassword())) {
                    currentUser =user;
                    SessionManager.setCurrentUser(currentUser);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPageDES.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage userPageStage = new Stage();
                    userPageStage.setScene(scene);
                    userPageStage.show();

                    ((Stage) usernameEntered.getScene().getWindow()).close();




            } else {

                    wrongLabel.setText("wrong username or password");
                }

            }
        }
    }
}