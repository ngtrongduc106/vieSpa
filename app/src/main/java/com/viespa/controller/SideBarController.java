package com.viespa.controller;

import com.viespa.App;
import com.viespa.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    User user = User.getInstance();

    //Set name
    final StringProperty userFullNameValue = new SimpleStringProperty(user.getFullname());
    //Button Logout
    @FXML
    Button button_staff;
    @FXML
    private Button course;
    @FXML
    private Button customer;
    @FXML
    private Button home;
    @FXML
    private Button transaction;


    public String getUserNameValue() {
        return "Hi! " + userFullNameValue.get();
    }

    @FXML
    public void buttonLogout() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out?");
        int currentPage = user.getPage();

        Optional<ButtonType> option = alert.showAndWait();
        if (option.isEmpty()) {
            return;
        }
        if (option.get() == ButtonType.OK) {
            user.setPage(0);
            App.setRoot("views/login-view");
        } else {
            user.setPage(currentPage);
        }

    }

    @FXML
    public void buttonStaff() throws IOException {
        user.setPage(1);
        App.setRoot("views/staff-view");
    }

    @FXML
    public void buttonHome() throws IOException {
        user.setPage(0);
        App.setRoot("views/home-view");
    }

    @FXML
    public void buttonCustomer() throws IOException {
        user.setPage(2);
        App.setRoot("views/customer-view");
    }

    @FXML
    public void buttonCourse() throws IOException {
        user.setPage(3);
        App.setRoot("views/course-view");
    }

    @FXML
    public void buttonTransaction() throws IOException {
        user.setPage(4);
        App.setRoot("views/transaction-view");
    }

    @FXML
    void buttonSetting() throws IOException {
        user.setPage(5);
        App.setRoot("views/setting-view");
    }

    public void author_active() {
        if (user.getRole() > 2) {
            button_staff.setDisable(true);
            home.setDisable(false);
            course.setDisable(false);
            transaction.setDisable(false);
            customer.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        author_active();
        switch (user.getPage()) {
            case 0:
                home.setDisable(true);
                home.getStyleClass().add("active");
                break;
            case 1:
                button_staff.setDisable(true);
                button_staff.getStyleClass().add("active");
                break;
            case 2:
                customer.setDisable(true);
                customer.getStyleClass().add("active");
                break;
            case 3:
                course.setDisable(true);
                course.getStyleClass().add("active");
                break;
            case 4:
                transaction.setDisable(true);
                transaction.getStyleClass().add("active");
                break;
            case 5:
            default:
                break;
        }
    }

}
