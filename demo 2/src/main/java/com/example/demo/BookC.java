
package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;


public class BookC extends Main implements Initializable {
    @FXML
    private String filePath = FileManger.getCurrentFilePath();

    @FXML
    private TableColumn<Ticket, String> titleColumn;

    @FXML
    private TableColumn<Ticket, Integer> bookedColumn;

    @FXML
    private TableView<Ticket> tableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookedColumn.setCellValueFactory(new PropertyValueFactory<>("booked")); // Use the correct property
        loadDataFromFile(filePath);
    }

    public void loadDataFromFile(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        for (String[] entry : data) {

            tableView.getItems().add(new Ticket(entry[0], Integer.parseInt(entry[1])));
        }
    }
    private Stage stage;
    private Scene scene;

    private Parent root;
    @FXML
    void BackB(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPageDES.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
