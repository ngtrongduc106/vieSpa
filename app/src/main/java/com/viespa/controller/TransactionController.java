package com.viespa.controller;

import com.viespa.App;
import com.viespa.models.*;
import com.viespa.utils.DateForm;
import com.viespa.utils.Invoice;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    TableColumn<Transaction , String> column_booking;

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

    @FXML
    TextArea input_note;

    @FXML
    DatePicker input_booking;

    @FXML
    TextField input_pay;

    @FXML
    private Button buttonCancel;

    @FXML
    void buttonCancel() {

    }

    @FXML
    void addCustomer() throws IOException {
        User.getInstance().setPage(2);
        App.setRoot("views/customer-view");
    }


    int id ;
    int myIndex;

    public void table(){
        ObservableList<Transaction> transactions = Transaction.getAllTransaction();
        table_transaction.setItems(transactions);
        column_number.setCellValueFactory(f -> f.getValue().idProperty());
        column_customer.setCellValueFactory(f -> f.getValue().customerProperty());
        column_course.setCellValueFactory(f -> f.getValue().courseProperty());
        column_staff.setCellValueFactory(f -> f.getValue().staffProperty());
        column_booking.setCellValueFactory(f -> DateForm.convert(String.valueOf(f.getValue().bookingProperty().getValue())));
        column_note.setCellValueFactory(f -> f.getValue().noteProperty());
        column_createby.setCellValueFactory(f -> f.getValue().createByProperty());
        column_pay.setCellValueFactory(f -> f.getValue().payProperty());

        button_update.setDisable(true);
        button_add.setDisable(false);
        input_pay.setDisable(true);

        table_transaction.setRowFactory(it -> {
            TableRow<Transaction> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table_transaction.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table_transaction.getItems().get(myIndex).getId()));
                    input_customer.setValue(table_transaction
                            .getItems()
                            .get(myIndex)
                            .getCustomer());
                    input_course.setValue(table_transaction
                            .getItems()
                            .get(myIndex)
                            .getCourse());
                    input_staff.setValue(table_transaction
                            .getItems()
                            .get(myIndex)
                            .getStaff());
                    input_booking.setValue(table_transaction
                            .getItems()
                            .get(myIndex)
                            .getBooking());
                    input_note.setText(table_transaction
                            .getItems()
                            .get(myIndex)
                            .getNote());
                    input_pay.setText(table_transaction
                            .getItems()
                            .get(myIndex)
                            .getPay());

                    button_add.setDisable(true);
                    button_update.setDisable(false);
                    input_pay.setDisable(false);
                }
            });
            return myRow;
        });
    }

    public void button_add() throws Exception {
        String val_customer = Customer.queryId(input_customer.getValue());
        String val_course = Course.queryId(input_course.getValue());
        String val_staff = Staff.queryId(input_staff.getValue());
        String val_price = Course.queryPrice(val_course);
        String val_createby = String.valueOf(User.getInstance().getId());
        String val_note = input_note.getText().trim();
        LocalDate val_booking = input_booking.getValue();

        if(val_customer == null || val_course == null || val_staff == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }else {
            Transaction.addNew(val_customer,val_course,val_staff,val_price,val_note,val_booking,val_createby);

            input_customer.setValue("");
            input_course.setValue("");
            input_staff.setValue("");
            input_booking.setValue(null);
            input_note.setText("");
            table();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to create invoice?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Invoice.print(Transaction.getLastInput());
        }
    }

    public void button_update() throws Exception {
        if(User.getInstance().getId() != 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You don't have author !");
            alert.show();
            return;
        }
        String val_customer = Customer.queryId(input_customer.getValue());
        String val_course = Course.queryId(input_course.getValue());
        String val_staff = Staff.queryId(input_staff.getValue());
        String val_pay = input_pay.getText().trim();
        String val_createby = String.valueOf(User.getInstance().getId());
        String val_note = input_note.getText();
        LocalDate val_booking = input_booking.getValue();

        if(val_customer == null || val_course == null || val_staff == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Admin can't be a staff");
            alert.show();
        }else {
            Transaction.update(val_customer,val_course,val_staff,val_pay,val_note,val_booking, String.valueOf(id));

            input_customer.setValue("");
            input_course.setValue("");
            input_staff.setValue("");
            input_booking.setValue(null);
            input_note.setText("");
            input_pay.setText("");
            table();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update invoice?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Invoice.print(Transaction.getById(id));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customers = Customer.getAllCustomers();
        customers.stream().map(Customer::getFullName).forEach(t -> input_customer.getItems().add(t));

        ObservableList<Course> courses = Course.getAllCourses();
        courses.stream().map(Course::getName).forEach(t -> input_course.getItems().add(t));

        ObservableList<Staff> staffs = Staff.getAllStaffs();
        staffs.stream().map(Staff::getFullname).forEach(t -> input_staff.getItems().add(t));

        table();
    }
}
