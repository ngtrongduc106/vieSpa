package com.viespa.controller;

import com.viespa.models.Customer;
import com.viespa.models.Staff;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    TableView<Customer> table_customer;

    @FXML
    TableColumn<Customer , String> column_fullname;

    @FXML
    TableColumn<Customer , String> column_phone;

    @FXML
    TableColumn<Customer , String> column_address;

    @FXML
    TableColumn<Customer , String> column_email;

    @FXML
    TableColumn<Customer , LocalDate> column_dob;

    @FXML
    Button button_add;

    @FXML
    Button button_update;

    @FXML
    TextField input_fullname;

    @FXML
    TextField input_email;

    @FXML
    TextField input_phone;

    @FXML
    TextField input_address;

    @FXML
    DatePicker input_dob;

    int id ;
    int myIndex;

    public void table(){
        ObservableList<Customer> customers = Customer.getAllCustomers();
        table_customer.setItems(customers);
        column_fullname.setCellValueFactory(f -> f.getValue().fullNameProperty());
        column_phone.setCellValueFactory(f -> f.getValue().phoneProperty());
        column_address.setCellValueFactory(f -> f.getValue().addressProperty());
        column_email.setCellValueFactory(f -> f.getValue().emailProperty());
        column_dob.setCellValueFactory(f -> f.getValue().dobProperty());

        button_update.setDisable(true);
        button_add.setDisable(false);

        table_customer.setRowFactory(it -> {
            TableRow<Customer> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table_customer.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table_customer.getItems().get(myIndex).getId()));
                    input_fullname.setText(table_customer
                            .getItems()
                            .get(myIndex)
                            .getFullName());
                    input_phone.setText(table_customer
                            .getItems()
                            .get(myIndex)
                            .getPhone());
                    input_email.setText(table_customer
                            .getItems()
                            .get(myIndex)
                            .getPhone());
                    input_address.setText(table_customer
                            .getItems()
                            .get(myIndex)
                            .getAddress());
                    input_dob.setValue(table_customer
                            .getItems()
                            .get(myIndex)
                            .getDob());

                    button_add.setDisable(true);
                    button_update.setDisable(false);
                }
            });
            return myRow;
        });
    }

    @FXML
    public void button_add() throws SQLException {

        String val_fullname = input_fullname.getText().trim();
        String val_phone = input_phone.getText().trim();
        String val_email = input_email.getText().trim();
        String val_address = input_address.getText().trim();
        LocalDate val_dob = input_dob.getValue();

        if(val_fullname.isEmpty()){
            return;
        }else {
            Customer.addNewCustomer(val_fullname,val_phone,val_email,val_address,val_dob);
            input_fullname.setText("");
            input_phone.setText("");
            input_email.setText("");
            input_address.setText("");
            input_dob.setValue(null);
        }
        table();
    }

    @FXML
    public void button_update() throws SQLException {
        myIndex = table_customer.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table_customer.getItems().get(myIndex).getId()));

        String val_fullname = input_fullname.getText().trim();
        String val_phone = input_phone.getText().trim();
        String val_email = input_email.getText().trim();
        String val_address = input_address.getText().trim();
        LocalDate val_dob = input_dob.getValue();

        if(val_fullname.isEmpty()){
            return;
        }else {
            Customer.updateCustomer(val_fullname,val_phone,val_email,val_address,val_dob,id);
            input_fullname.setText("");
            input_phone.setText("");
            input_email.setText("");
            input_address.setText("");
            input_dob.setValue(null);
        }

        table();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
    }
}