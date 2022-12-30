package com.viespa.controller;

import com.viespa.models.Course;
import com.viespa.models.Customer;
import com.viespa.models.Staff;
import com.viespa.models.Transaction;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    @FXML
    TableView<Transaction> table_transaction;

    @FXML
    TableColumn<Transaction , String> column_number;

    @FXML
    TableColumn<Transaction , String> column_customer;

    @FXML
    TableColumn<Transaction , String> column_course;

    @FXML
    TableColumn<Transaction , String> column_staff;

    @FXML
    TableColumn<Transaction , LocalDate> column_booking;

    @FXML
    TableColumn<Transaction , String> column_note;

    @FXML
    TableColumn<Transaction , String> column_createby;

    @FXML
    TableColumn<Transaction , String> column_pay;

    @FXML
    Button button_add;

    @FXML
    Button button_update;

    @FXML
    ChoiceBox<String> input_customer = new ChoiceBox<>();

    @FXML
    ChoiceBox<String> input_course = new ChoiceBox<>();

    @FXML
    ChoiceBox<String> input_staff = new ChoiceBox<>();

    public void table(){
        ObservableList<Transaction> transactions = Transaction.getAllTransaction();
        table_transaction.setItems(transactions);
        column_number.setCellValueFactory(f -> f.getValue().idProperty());
        column_customer.setCellValueFactory(f -> f.getValue().customerProperty());
        column_course.setCellValueFactory(f -> f.getValue().courseProperty());
        column_staff.setCellValueFactory(f -> f.getValue().staffProperty());
        column_booking.setCellValueFactory(f -> f.getValue().bookingProperty());
        column_note.setCellValueFactory(f -> f.getValue().noteProperty());
        column_createby.setCellValueFactory(f -> f.getValue().createByProperty());
        column_pay.setCellValueFactory(f -> f.getValue().payProperty());
    }

    public void button_add(){
        System.out.println(input_customer.getValue());
    }

    public void button_update(){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
        ObservableList<Customer> customers = Customer.getAllCustomers();
        customers.stream().map(Customer::getFullName).forEach(t -> input_customer.getItems().add(t));

        ObservableList<Course> courses = Course.getAllCourses();
        courses.stream().map(Course::getName).forEach(t -> input_course.getItems().add(t));

        ObservableList<Staff> staffs = Staff.getAllStaffs();
        staffs.stream().map(Staff::getFullname).forEach(t -> input_staff.getItems().add(t));


    }
}
