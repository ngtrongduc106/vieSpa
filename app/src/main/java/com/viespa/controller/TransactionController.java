package com.viespa.controller;

import com.viespa.App;
import com.viespa.models.Course;
import com.viespa.models.Customer;
import com.viespa.models.Staff;
import com.viespa.models.Transaction;
import com.viespa.models.User;
import com.viespa.utils.AutoCompleteComboBoxListener;
import com.viespa.utils.ContractUtil;
import com.viespa.utils.CurrencyUtil;
import com.viespa.utils.DateUtil;
import com.viespa.utils.InvoiceUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    @FXML
    private TextField input_search;
    @FXML
    TableView<Transaction> table_transaction;

    @FXML
    TableColumn<Transaction, String> column_number;

    @FXML
    TableColumn<Transaction, String> column_customer;

    @FXML
    TableColumn<Transaction, String> column_course;

    @FXML
    TableColumn<Transaction, String> column_staff;

    @FXML
    TableColumn<Transaction, String> column_booking;

    @FXML
    TableColumn<Transaction, String> column_note;

    @FXML
    TableColumn<Transaction, String> column_createby;

    @FXML
    TableColumn<Transaction, String> column_pay;

    @FXML
    Button button_add;

    @FXML
    Button button_update;

    @FXML
    ComboBox<String> input_customer = new ComboBox<>();;

    @FXML
    ComboBox<String> input_course = new ComboBox<>();

    @FXML
    ChoiceBox<String> input_staff = new ChoiceBox<>();

    @FXML
    TextArea input_note;

    @FXML
    DatePicker input_booking;

    @FXML
    TextField input_pay;
    int id;
    int myIndex;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button button_print;

    @FXML
    void buttonCancel() {
        input_customer.setValue("");
        input_course.setValue("");
        input_staff.setValue("");
        input_booking.setValue(null);
        input_note.setText("");
        input_pay.setText("");
        input_pay.setDisable(true);
        button_update.setDisable(true);
        button_add.setDisable(false);
        button_print.setDisable(true);
        table_transaction.getSelectionModel().select(null);
    }

    @FXML
    void addCustomer() throws IOException {
        User.getInstance().setPage(2);
        App.setRoot("views/customer-view");
    }

    public void table(ObservableList<Transaction> transactions) {
        table_transaction.setItems(transactions);
        column_number.setCellValueFactory(f -> f.getValue().idProperty());
        column_customer.setCellValueFactory(f -> f.getValue().customerProperty());
        column_course.setCellValueFactory(f -> f.getValue().courseProperty());
        column_staff.setCellValueFactory(f -> f.getValue().staffProperty());
        column_booking.setCellValueFactory(f -> DateUtil.convert(String.valueOf(f.getValue().bookingProperty().getValue())));
        column_note.setCellValueFactory(f -> f.getValue().noteProperty());
        column_createby.setCellValueFactory(f -> f.getValue().createByProperty());
        column_pay.setCellValueFactory(f -> CurrencyUtil.formatCur(f.getValue().payProperty().getValue()));

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
                    input_staff.setValue(String.valueOf(table_transaction
                            .getItems()
                            .get(myIndex)
                            .getStaff()));
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
                    button_print.setDisable(false);
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

        if (val_customer == null || val_course == null || val_staff == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Pls check all input, some inputs cannot be empty! Note that admin cannot be an staff");
            alert.showAndWait();
            return;
        }
        Transaction.addNew(val_customer, val_course, val_staff, val_price, val_note, val_booking, val_createby);

        input_customer.setValue("");
        input_course.setValue("");
        input_staff.setValue("");
        input_booking.setValue(null);
        input_note.setText("");
        table(Transaction.getAllTransaction());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Transaction created successfully!\nDo you want to create invoice and/or contract?", new ButtonType("Contract"), new ButtonType("Invoice"), new ButtonType("Both"), ButtonType.CANCEL);
        alert.showAndWait();

        switch (alert.getResult().getText()) {
            case "Contract":
                ContractUtil.print(Transaction.getLastInput());
                break;
            case "Invoice":
                InvoiceUtil.print(Transaction.getLastInput());
                break;
            case "Both":
                ContractUtil.print(Transaction.getLastInput());
                InvoiceUtil.print(Transaction.getLastInput());
            default:
                break;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION, "What do you want to print?", new ButtonType("Contract"), new ButtonType("Invoice"), new ButtonType("Both"), ButtonType.CANCEL);
        alert.showAndWait();

        switch (alert.getResult().getText()) {
            case "Contract":
                Runtime.getRuntime().exec("cmd /c ..\\contracts\\contract_" + Transaction.getLastInput().getId() + ".html");
                break;
            case "Invoice":
                Runtime.getRuntime().exec("cmd /c ..\\invoices\\invoice_" + Transaction.getLastInput().getId() + ".html");
                break;
            case "Both":
                Runtime.getRuntime().exec("cmd /c ..\\contracts\\contract_" + Transaction.getLastInput().getId() + ".html");
                Runtime.getRuntime().exec("cmd /c ..\\invoices\\invoice_" + Transaction.getLastInput().getId() + ".html");
            default:
                break;
        }

    }

    public void button_update() throws Exception {
        if (User.getInstance().getRole() > 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insufficient permission!");
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

        if (val_customer == null || val_course == null || val_staff == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Pls check all input, some inputs cannot be empty! Note that admin cannot be an staff");
            alert.show();
            return;
        }
        Transaction.update(val_customer, val_course, val_staff, val_pay, val_note, val_booking, String.valueOf(id));

        input_customer.setValue("");
        input_course.setValue("");
        input_staff.setValue("");
        input_booking.setValue(null);
        input_note.setText("");
        input_pay.setText("");
        button_print.setDisable(true);
        ;
        table(Transaction.getAllTransaction());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Transaction updated successfully!\nDo you want to modify invoice and/or contract?", new ButtonType("Contract"), new ButtonType("Invoice"), new ButtonType("Both"), ButtonType.CANCEL);
        alert.showAndWait();

        switch (alert.getResult().getText()) {
            case "Contract":
                ContractUtil.print(Transaction.getById(id));
                break;
            case "Invoice":
                InvoiceUtil.print(Transaction.getById(id));
                break;
            case "Both":
                ContractUtil.print(Transaction.getById(id));
                InvoiceUtil.print(Transaction.getById(id));
            default:
                break;
        }
    }

    @FXML
    void onPrint() {
        try {
            ContractUtil.print(Transaction.getById(id));
            InvoiceUtil.print(Transaction.getById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "What do you want to print?", new ButtonType("Contract"), new ButtonType("Invoice"), new ButtonType("Both"), ButtonType.CANCEL);
        alert.showAndWait();
        try {
            switch (alert.getResult().getText()) {
                case "Contract":
                    Runtime.getRuntime().exec("cmd /c ..\\contracts\\contract_" + id + ".html");
                    break;
                case "Invoice":
                    Runtime.getRuntime().exec("cmd /c ..\\invoices\\invoice_" + id + ".html");
                    break;
                case "Both":
                    Runtime.getRuntime().exec("cmd /c ..\\contracts\\contract_" + id + ".html");
                    Runtime.getRuntime().exec("cmd /c ..\\invoices\\invoice_" + id + ".html");
                default:
                    break;
            }
        } catch (Exception ex) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File does not exist");
            alert.showAndWait();
        }
    }

    public void search(){
        table(Transaction.search(input_search.getText()));
    }

    String customer =" ";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        input_customer.setEditable(true);
        new AutoCompleteComboBoxListener<>(input_customer);

        input_course.setEditable(true);
        new AutoCompleteComboBoxListener<>(input_course);

        ObservableList<Customer> customers = Customer.getAllCustomers();
        customers.stream().map(Customer::getFullName).forEach(t -> input_customer.getItems().add(t));

        ObservableList<Course> courses = Course.getActiveCourses();
        courses.stream().map(Course::getName).forEach(t -> input_course.getItems().add(t));

        ObservableList<Staff> staffs = Staff.getAllStaffs();
        staffs.stream().map(Staff::getFullname).forEach(t -> input_staff.getItems().add(t));

        table(Transaction.getAllTransaction());
    }
}
