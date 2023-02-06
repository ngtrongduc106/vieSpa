package com.viespa.controller;

import com.viespa.models.Course;
import com.viespa.models.User;
import com.viespa.utils.AlertUtil;
import com.viespa.utils.CurrencyUtil;
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
        column_price.setCellValueFactory(f -> CurrencyUtil.formatCur(f.getValue().priceProperty().getValue()));
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
                    input_status.setValue(table_course.getItems().get(myIndex).getActive().equals("1") ? "Available" : "Unavailable");


                    button_add.setDisable(true);
                    button_update.setDisable(false);
                }
            });
            return myRow;
        });
    }

    public void button_add() throws SQLException {
        if (User.getInstance().getRole() > 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insufficient permission!");
            alert.show();
            return;
        }
        String val_name = input_name.getText().trim();
        String val_price = input_price.getText().trim();
        String val_description = input_description.getText().trim();
        String val_status = input_status.getValue().equals("Available") ? "1" : "0";

        if (val_name.isEmpty() || val_price.isEmpty() || val_description.isEmpty()) {
            AlertUtil.showError("Inputs can not be empty");
            return;
        }
        Course.addNew(val_name, val_price, val_description, val_status);
        input_name.setText("");
        input_price.setText("");
        input_description.setText("");
        input_status.setValue("");
        table();
    }

    public void button_update() throws SQLException {
        if (User.getInstance().getRole() > 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insufficient permission!");
            alert.show();
            return;
        }
        myIndex = table_course.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table_course.getItems().get(myIndex).getId()));

        String val_name = input_name.getText().trim();
        String val_price = input_price.getText().trim();
        String val_description = input_description.getText().trim();

        String val_status = input_status.getValue().equals("Available") ? "1" : "0";


        if (val_name.isEmpty() || val_price.isEmpty() || val_description.isEmpty()) {
            AlertUtil.showError("Inputs can not be empty");
            return;
        }
        Course.update(String.valueOf(id), val_name, val_price, val_description, val_status);
        input_name.setText("");
        input_price.setText("");
        input_description.setText("");
        input_status.setValue("");
        button_update.setDisable(true);

        table();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
        input_status.getItems().add("Available");
        input_status.setValue("Available");
        input_status.getItems().add("Unavailable");
    }
}
