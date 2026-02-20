package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class UserC extends Main implements Initializable {
    private User newCurrentUser=SessionManager.getCurrentUser();

    private String userUsername=newCurrentUser.getUsername();

    private String userFileName=userUsername+".txt";

    @FXML
    private TableColumn<EventItem, Integer> capacity;

    @FXML
    private TableColumn<EventItem, String> category;

    @FXML
    private TableColumn<EventItem, String> title;

    @FXML
    private TableColumn<EventItem, String> date;

    @FXML
    private TableColumn<EventItem, String> time;

    @FXML
    private TableColumn<EventItem, String> Location;

    @FXML
    private TableColumn<EventItem, String> description;

    @FXML
    private TableView<EventItem> table;
    @FXML
    private Label errorL;
    private static final String FILE_PATH = "event_data.txt";

    private ObservableList<EventItem> eventData = FXCollections.observableArrayList();

    public UserC() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));


        loadDataFromFile();
        table.setItems(eventData);
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    EventItem event = new EventItem(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), parts[6]);
                    eventData.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField SearchField;
    @FXML
    private void oKBTN(ActionEvent event) {
        String searchedTitle = SearchField.getText();
        if (!searchedTitle.isEmpty()) {
            for (EventItem item : eventData) {
                if (item.getTitle().equalsIgnoreCase(searchedTitle)) {
                    eventData.remove(item);
                    eventData.add(0, item);
                    table.getSelectionModel().select(item);
                    table.scrollTo(item);
                    break;
                }
            }
        }

    }
///
    @FXML
     TextField NUM;
    @FXML
     TextField title1 ;
   @FXML

   FileWriter fileWriter=new FileWriter(userFileName);
   @FXML
    void booknow(ActionEvent event) throws IOException {

        String numberOfTicketsStr = NUM.getText();
        String eventTitle = title1.getText();

        if (!numberOfTicketsStr.isEmpty()) {
            int numberOfTickets = Integer.parseInt(numberOfTicketsStr);
            if (numberOfTickets <= 0) {
                errorL.setText("Number of tickets should be positive.");
                return;
            }
        } else {
            errorL.setText("Number of tickets cannot be empty.");
            return;
        }


       try (FileWriter fileWriter = new FileWriter(userFileName, true)) {
           fileWriter.write(eventTitle + "," + numberOfTicketsStr + "\n");
       } catch (IOException e) {
           e.printStackTrace();
       }

        if (!numberOfTicketsStr.isEmpty() && !eventTitle.isEmpty()) {
            int numberOfTickets = Integer.parseInt(numberOfTicketsStr);
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + ".tmp"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 7 && parts[0].equalsIgnoreCase(eventTitle)) {
                        int currentCapacity = Integer.parseInt(parts[5]);
                        if (currentCapacity >= numberOfTickets) {
                            int updatedCapacity = currentCapacity - numberOfTickets;
                            writer.write(parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + updatedCapacity + "," + parts[6]);
                            writer.newLine();
                            // يجدد
                            for (EventItem item : eventData) {
                                if (item.getTitle().equalsIgnoreCase(eventTitle)) {
                                    item.setCapacity(updatedCapacity);
                                    break;
                                }
                            }
                        } else {
                            errorL.setText("Not enough capacity available for booking.");
                            writer.write(line);
                            writer.newLine();
                        }
                    } else {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            File originalFile = new File(FILE_PATH);
            File tempFile = new File(FILE_PATH + ".tmp");
            if (tempFile.renameTo(originalFile)) {
                System.out.println("Capacity updated successfully.");
                table.refresh();
            } else {
                System.out.println("Failed to update capacity.");
            }
        }
    }






    private Stage stage;
    private Scene scene;

    private Parent root;
    @FXML
    void BackFu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void View(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingS.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

