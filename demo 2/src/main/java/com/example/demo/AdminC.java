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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class AdminC extends Main implements Initializable {


    private static ObservableList<EventItem> eventData = FXCollections.observableArrayList();

    @FXML
    private TextField Capacitytxt;

    @FXML
    private TextField Categorytxt;

    @FXML
    private TextField Datetxt;

    @FXML
    private TextField Descriptiontxt;

    @FXML
    private TextField Locationtxt;

    @FXML
    private TextField Timetxt;

    @FXML
    private TextField Titletxt;

    @FXML
    private TableColumn<EventItem, Integer> capacity;

    @FXML
    private TableColumn<EventItem, String> category;

    @FXML
    private TableColumn<EventItem, String> title;
    @FXML
    private Label ELabel;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));


        table.setItems(eventData);


        editData();
    }


    @FXML
    private void add(ActionEvent event) {
        if (!Titletxt.getText().isEmpty() && !Categorytxt.getText().isEmpty() && !Datetxt.getText().isEmpty() && !Timetxt.getText().isEmpty() && !Locationtxt.getText().isEmpty() && !Capacitytxt.getText().isEmpty() && !Descriptiontxt.getText().isEmpty()) {
            int capacity = Integer.parseInt(Capacitytxt.getText());
            if (capacity > 0) {
                String inputDate = Datetxt.getText();
                if (isValidDateFormat(inputDate)) {
                    try {
                        LocalDate enteredDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate currentDate = LocalDate.now();
                        if (!enteredDate.isBefore(currentDate)) {
                            EventItem newData = new EventItem(Titletxt.getText(), Categorytxt.getText(), inputDate, Timetxt.getText(), Locationtxt.getText(), capacity, Descriptiontxt.getText());

                            eventData.add(newData);

                            saveDataToFile(newData);

                            Titletxt.clear();
                            Categorytxt.clear();
                            Datetxt.clear();
                            Timetxt.clear();
                            Locationtxt.clear();
                            Capacitytxt.clear();
                            Descriptiontxt.clear();

                            ELabel.setText("Data added successfully.");
                        } else {
                            ELabel.setText("Date cannot be in the past.");
                        }
                    } catch (DateTimeParseException e) {
                        ELabel.setText("Invalid date format.");
                    }
                } else {
                    ELabel.setText("Invalid date format.");
                }
            } else {
                ELabel.setText("Capacity should be a positive number.");
            }
        } else {
            ELabel.setText("Fields should not be empty.");
        }
    }

    private boolean isValidDateFormat(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private void saveDataToFile(EventItem newData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("event_data.txt", true))) {
            writer.println(newData.getTitle() + "," + newData.getCategory() + "," + newData.getDate() + "," + newData.getTime() + "," + newData.getLocation() + "," + newData.getCapacity() + "," + newData.getDescription());
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }


    @FXML
    private void deleteData(ActionEvent event) {

        ObservableList<EventItem> selectedItems = table.getSelectionModel().getSelectedItems();
        table.getItems().removeAll(selectedItems);


        removeDataFromFile(selectedItems);
        ELabel.setText(  selectedItems.size() + " is Deleted Successfully");
    }

    private void removeDataFromFile(ObservableList<EventItem> itemsToRemove) {
        try {

            List<String> lines = Files.readAllLines(Paths.get("event_data.txt"));

            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();

                for (EventItem item : itemsToRemove) {
                    if (line.startsWith(item.getTitle() + ",")) {
                        iterator.remove();
                        break;
                    }
                }
            }

            Files.write(Paths.get("event_data.txt"), lines);
        } catch (IOException e) {
            System.out.println("Error removing data from file: " + e.getMessage());
        }
    }

    @FXML
    private TextField sField;
    @FXML
    void DoneBTN(ActionEvent event) {
        String SearchedTitle = sField.getText();
        if (!SearchedTitle.isEmpty()) {
            for (EventItem item : eventData) {
                if (item.getTitle().equalsIgnoreCase(SearchedTitle)) {
                    eventData.remove(item);
                    eventData.add(0, item);
                    table.getSelectionModel().select(item);
                    table.scrollTo(item);
                    break;
                }
            }
        }
    }





    private void editData() {
        title.setCellFactory(TextFieldTableCell.<EventItem>forTableColumn());
        title.setOnEditCommit(event -> {
            EventItem eventData = event.getTableView().getItems().get(event.getTablePosition().getRow());
            eventData.setTitle(event.getNewValue());
            System.out.println(eventData.getTitle() + "'s Title was updated to " + event.getNewValue() + " at row " + (event.getTablePosition().getRow() + 1));
        });

        category.setCellFactory(TextFieldTableCell.<EventItem>forTableColumn());
        category.setOnEditCommit(event -> {
            EventItem eventData = event.getTableView().getItems().get(event.getTablePosition().getRow());
            eventData.setCategory(event.getNewValue());
            System.out.println(eventData.getTitle() + "'s Category was updated to " + event.getNewValue() + " at row " + (event.getTablePosition().getRow() + 1));
        });

        date.setCellFactory(TextFieldTableCell.<EventItem>forTableColumn());
        date.setOnEditCommit(event -> {
            EventItem eventData = event.getTableView().getItems().get(event.getTablePosition().getRow());
            eventData.setDate(event.getNewValue());
            System.out.println("Date was updated to " + event.getNewValue() + " at row " + (event.getTablePosition().getRow() + 1));
        });

        time.setCellFactory(TextFieldTableCell.<EventItem>forTableColumn());
        time.setOnEditCommit(event -> {
            EventItem eventData = event.getTableView().getItems().get(event.getTablePosition().getRow());
            eventData.setTime(event.getNewValue());
            System.out.println("Time was updated to " + event.getNewValue() + " at row " + (event.getTablePosition().getRow() + 1));
        });

        Location.setCellFactory(TextFieldTableCell.<EventItem>forTableColumn());
        Location.setOnEditCommit(event -> {
            EventItem eventData = event.getTableView().getItems().get(event.getTablePosition().getRow());
            eventData.setLocation(event.getNewValue());
            System.out.println("Location was updated to " + event.getNewValue() + " at row " + (event.getTablePosition().getRow() + 1));
        });

        capacity.setCellFactory(TextFieldTableCell.<EventItem, Integer>forTableColumn(new IntegerStringConverter()));
        capacity.setOnEditCommit(event -> {
            EventItem eventData = event.getTableView().getItems().get(event.getTablePosition().getRow());
            eventData.setCapacity(event.getNewValue());
            System.out.println("Capacity was updated to " + event.getNewValue() + " at row " + (event.getTablePosition().getRow() + 1));
        });

        description.setCellFactory(TextFieldTableCell.<EventItem>forTableColumn());
        description.setOnEditCommit(event -> {
            EventItem eventData = event.getTableView().getItems().get(event.getTablePosition().getRow());
            eventData.setDescription(event.getNewValue());
            System.out.println("Description was updated to " + event.getNewValue() + " at row " + (event.getTablePosition().getRow() + 1));
        });
    }

    private Stage stage;
    private Scene scene;

    private Parent root;
    @FXML
    void BackFa(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
