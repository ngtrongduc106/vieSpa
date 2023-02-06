package com.viespa.controller;

import com.viespa.models.Customer;
import com.viespa.utils.AlertUtil;
import com.viespa.utils.DateUtil;
import com.viespa.utils.RegexUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private TextField input_search;
    @FXML
    TableView<Customer> table_customer;

    @FXML
    TableColumn<Customer, String> column_fullname;

    @FXML
    TableColumn<Customer, String> column_phone;

    @FXML
    TableColumn<Customer, String> column_address;

    @FXML
    TableColumn<Customer, String> column_email;

    @FXML
    TableColumn<Customer, String> column_dob;

    @FXML
    TableColumn<Customer, String> column_gender;

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

    @FXML
    private ChoiceBox<String> input_gender;

    int id;
    int myIndex;
    String phoneRegex = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonChangeStatus;

    @FXML
    void buttonCancel() {
        input_fullname.setText("");
        input_phone.setText("");
        input_email.setText("");
        input_address.setText("");
        input_dob.setValue(null);
        input_gender.setValue("");
        button_update.setDisable(true);
        button_add.setDisable(false);
        table_customer.getSelectionModel().select(null);
    }

    @FXML
    void buttonChangeStatus() {

    }

    public void table(ObservableList<Customer> customers) {
        table_customer.setItems(customers);
        column_fullname.setCellValueFactory(f -> f.getValue().fullNameProperty());
        column_phone.setCellValueFactory(f -> f.getValue().phoneProperty());
        column_gender.setCellValueFactory(f -> f.getValue().isFemeleProperty().getValue().equals("0")
                ? new SimpleStringProperty("Male")
                : new SimpleStringProperty("Female"));
        column_address.setCellValueFactory(f -> f.getValue().addressProperty());
        column_email.setCellValueFactory(f -> f.getValue().emailProperty());
        column_dob.setCellValueFactory(f -> DateUtil.convert(String.valueOf(f.getValue().dobProperty().getValue())));
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
                            .getEmail());
                    input_address.setText(table_customer
                            .getItems()
                            .get(myIndex)
                            .getAddress());
                    input_dob.setValue(table_customer
                            .getItems()
                            .get(myIndex)
                            .getDob());
                    input_gender.setValue(table_customer.getItems().get(myIndex).getIs_female().equals("0") ? "Male" : "Female");

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
        String val_phone = RegexUtil.validate(input_phone.getText().trim(), phoneRegex);
        String val_email = RegexUtil.validate(input_email.getText().trim(), emailRegex);
        String val_address = input_address.getText();
        LocalDate val_dob = input_dob.getValue();
        String val_gender = input_gender.getValue().equals("Female") ? "1" : "0";

        if (val_phone.equals("0")) {
            AlertUtil.showError("Phone wrong format !");
            return;
        }

        if (val_email.equals("0")) {
            AlertUtil.showError("Email wrong format, e.g: test@mail.com");
            return;
        }

        if (val_fullname.isEmpty() || val_phone.isEmpty() || val_email.isEmpty() || val_dob == null) {
            AlertUtil.showError("Input can not empty for this request");
            return;
        }
        Customer.addNewCustomer(val_fullname, val_phone, val_email, val_address, val_gender, val_dob);
        input_fullname.setText("");
        input_phone.setText("");
        input_email.setText("");
        input_address.setText("");
        input_dob.setValue(null);
        input_gender.setValue("");
        table(Customer.getAllCustomers());
    }

    @FXML
    public void button_update() throws SQLException {
        myIndex = table_customer.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table_customer.getItems().get(myIndex).getId()));

        String val_fullname = input_fullname.getText().trim();
        String val_phone = RegexUtil.validate(input_phone.getText().trim(), phoneRegex);
        String val_email = RegexUtil.validate(input_email.getText().trim(), emailRegex);
        String val_address = input_address.getText();
        LocalDate val_dob = input_dob.getValue();
        String val_gender = input_gender.getValue().equals("Female") ? "1" : "0";

        if (val_phone.equals("0")) {
            AlertUtil.showError("Incorrect phone format!");
            return;
        }

        if (val_email.equals("0")) {
            AlertUtil.showError("incorrect email format, e.g: test@mail.com");
            return;
        }

        if (val_fullname.isEmpty() || val_phone.isEmpty() || val_email.isEmpty() || val_dob == null) {
            AlertUtil.showError("Input can not be empty");
            return;
        }
        Customer.updateCustomer(val_fullname, val_phone, val_email, val_address, id, val_gender, val_dob);
        input_fullname.setText("");
        input_phone.setText("");
        input_email.setText("");
        input_address.setText("");
        input_dob.setValue(null);
        input_gender.setValue("");
        table(Customer.getAllCustomers());
    }

    public void search() {
        table(Customer.search(input_search.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table(Customer.getAllCustomers());
        input_gender.getItems().add("Male");
        input_gender.setValue("Female");
        input_gender.getItems().add("Female");
    }
}
