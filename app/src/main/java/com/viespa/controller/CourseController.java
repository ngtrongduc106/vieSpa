package com.viespa.controller;

import com.viespa.models.Course;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
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
    Button button_add;

    @FXML
    Button button_update;

    @FXML
    TextField input_name;

    @FXML
    TextField input_price;

    @FXML
    TextArea input_description;
    int id;
    int myIndex;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonChangeStatus;

    @FXML
    void buttonCancel() {
        input_name.setText("");
        input_price.setText("");
        input_description.setText("");
        button_update.setDisable(true);
        button_add.setDisable(false);
        table_course.getSelectionModel().select(null);

    }

    @FXML
    void buttonChangeStatus() {

    }

    public void table() {
        ObservableList<Course> courses = Course.getAllCourses();
        table_course.setItems(courses);
        column_name.setCellValueFactory(f -> f.getValue().nameProperty());
        column_price.setCellValueFactory(f -> f.getValue().priceProperty());
        column_description.setCellValueFactory(f -> f.getValue().descriptionProperty());

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

        if (val_name.isEmpty()) {
            return;
        } else {
            Course.addNew(val_name, val_price, val_description);
            input_name.setText("");
            input_price.setText("");
            input_description.setText("");
        }

        table();
    }

    public void button_update() throws SQLException {
        myIndex = table_course.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table_course.getItems().get(myIndex).getId()));

        String val_name = input_name.getText().trim();
        String val_price = input_price.getText().trim();
        String val_description = input_description.getText().trim();

        if (val_name.isEmpty()) {
            return;
        } else {
            Course.update(String.valueOf(id), val_name, val_price, val_description);
            input_name.setText("");
            input_price.setText("");
            input_description.setText("");
            button_update.setDisable(true);
        }

        table();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
    }
}
