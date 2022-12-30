package com.viespa.controller;

import com.viespa.App;
import com.viespa.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    User user = User.getInstance();

    //Set name
    final StringProperty userFullNameValue = new SimpleStringProperty(user.getFullname());
    public String getUserNameValue(){
        return "Hi! " + userFullNameValue.get();
    }

    //Button Logout
    @FXML
    public void buttonLogout() throws IOException {
        App.setRoot("views/login-view");
    }

    @FXML
    Button button_staff;

    @FXML
    public void buttonStaff() throws IOException {
        App.setRoot("views/staff-view");
    }

    @FXML
    public void buttonHome() throws IOException {
        App.setRoot("views/home-view");
    }

    @FXML
    public void buttonCustomer() throws IOException {
        App.setRoot("views/customer-view");
    }

    @FXML
    public void buttonCourse() throws IOException {
        App.setRoot("views/course-view");
    }

    @FXML
    public void buttonTransaction() throws IOException {
        App.setRoot("views/transaction-view");
    }

    public void author_active(){
        if(user.getRole() == 2){
            button_staff.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        author_active();
    }

}
