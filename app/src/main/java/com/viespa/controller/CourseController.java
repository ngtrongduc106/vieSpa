package com.viespa.controller;

import com.viespa.models.Course;
import com.viespa.utils.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CourseController implements Initializable {
    @FXML
    TableView<Course> table_course;

    @FXML
    TableColumn<Course, String> column_name;

    @FXML
    TableColumn<Course, String> column_price;

    @FXML
    TableColumn<Course, String> column_description;

    @FXML
    private TableColumn<Course, String> column_status;

    @FXML
    Button button_add;

    @FXML
    Button button_update;

    @FXML
    TextField input_name;

    @FXML
    TextField input_price;

    @FXML
    TextArea input_description;

    @FXML
    private ChoiceBox<String> input_status;

    int id;
    int myIndex;
    @FXML
    private Button buttonCancel;

    @FXML
    void buttonCancel() {
        input_name.setText("");
        input_price.setText("");
        input_description.setText("");
        input_status.setValue("");
        button_update.setDisable(true);
        button_add.setDisable(false);
        table_course.getSelectionModel().select(null);

    }

    public void table() {
        ObservableList<Course> courses = Course.getAllCourses();
        table_course.setItems(courses);
        column_name.setCellValueFactory(f -> f.getValue().nameProperty());
        column_price.setCellValueFactory(f -> f.getValue().priceProperty());
        column_description.setCellValueFactory(f -> f.getValue().descriptionProperty());
        column_status.setCellValueFactory(f -> f.getValue().activeProperty().getValue().equals("1")
                ? new SimpleStringProperty("Available")
                : new SimpleStringProperty("Unavailable"));

        button_update.setDisable(true);
        button_add.setDisable(false);

        table_course.setRowFactory(it -> {
            TableRow<Course> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table_course.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table_course.getItems().get(myIndex).getId()));
                    input_name.setText(table_course
                            .getItems()
                            .get(myIndex)
                            .getName());
                    input_price.setText(table_course
                            .getItems()
                            .get(myIndex)
                            .getPrice());
                    input_description.setText(table_course
                            .getItems()
                            .get(myIndex)
                            .getDescription());
                    if (table_course.getItems().get(myIndex).getActive().equals("1")) {
                        input_status.setValue("Available");
                    } else {
                        input_status.setValue("Unvailable");
                    }

                    button_add.setDisable(true);
                    button_update.setDisable(false);
                }
            });
            return myRow;
        });
    }

    public void button_add() throws SQLException {
        String val_name = input_name.getText().trim();
        String val_price = input_price.getText().trim();
        String val_description = input_description.getText().trim();
        String val_status;

        if (Objects.equals(input_status.getValue(), "Available")) {
            val_status = "1";
        } else {
            val_status = "0";
        }

        if (val_name.isEmpty() || val_price.isEmpty() || val_description.isEmpty()) {
            AlertUtil.showError("Input can not empty for this request");
            return;
        } else {
            Course.addNew(val_name, val_price, val_description, val_status);
            input_name.setText("");
            input_price.setText("");
            input_description.setText("");
            input_status.setValue("");
        }

        table();
    }

    public void button_update() throws SQLException {
        myIndex = table_course.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table_course.getItems().get(myIndex).getId()));

        String val_name = input_name.getText().trim();
        String val_price = input_price.getText().trim();
        String val_description = input_description.getText().trim();

        String val_status;

        if (Objects.equals(input_status.getValue(), "Available")) {
            val_status = "1";
        } else {
            val_status = "0";
        }

        if (val_name.isEmpty() || val_price.isEmpty() || val_description.isEmpty()) {
            AlertUtil.showError("Input can not empty for this request");
            return;
        } else {
            Course.update(String.valueOf(id), val_name, val_price, val_description, val_status);
            input_name.setText("");
            input_price.setText("");
            input_description.setText("");
            input_status.setValue("");
            button_update.setDisable(true);
        }

        table();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
        input_status.getItems().add("Available");
        input_status.getItems().add("Unavailable");
    }
}
