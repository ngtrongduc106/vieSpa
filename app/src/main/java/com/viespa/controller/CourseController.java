package com.viespa.controller;

import com.viespa.models.Course;
import com.viespa.models.Staff;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseController implements Initializable {
    @FXML
    TableView<Course> table_course;

    @FXML
    TableColumn<Course , String> column_name;

    @FXML
    TableColumn<Course , String> column_price;

    @FXML
    TableColumn<Course , String> column_description;

    @FXML
    TableColumn<Course , String> column_createby;

    @FXML
    Button button_add;

    @FXML
    Button  button_update;

    public void table(){
        ObservableList<Course> courses = Course.getAllCourses();
        table_course.setItems(courses);
        column_name.setCellValueFactory(f -> f.getValue().nameProperty());
        column_price.setCellValueFactory(f -> f.getValue().priceProperty());
        column_description.setCellValueFactory(f -> f.getValue().descriptionProperty());
        column_createby.setCellValueFactory(f -> f.getValue().createByProperty());

        button_update.setDisable(true);
        button_add.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
    }
}
