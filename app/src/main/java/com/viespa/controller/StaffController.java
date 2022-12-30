package com.viespa.controller;

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

public class StaffController implements Initializable {
    @FXML
    TableView<Staff> table_staff ;

    @FXML
    TableColumn<Staff , String> column_fullname;

    @FXML
    TableColumn<Staff , String> column_dob;

    @FXML
    TableColumn<Staff , String> column_phone;

    @FXML
    TableColumn<Staff , String> column_email;

    @FXML
    TableColumn<Staff , String> column_address;

    @FXML
    TableColumn<Staff , String> column_role;

    @FXML
    TableColumn<Staff , LocalDate> column_joindate;

    @FXML
    TableColumn<Staff , LocalDate> column_enddate;

    @FXML
    TextField input_fullname;

    @FXML
    TextField input_phone;

    @FXML
    TextField input_email;

    @FXML
    TextField input_address;

    @FXML
    DatePicker input_dob;

    @FXML
    DatePicker input_joindate;

    @FXML
    DatePicker input_enddate;

    @FXML
    TextField input_account;

    @FXML
    TextField input_password;

    @FXML
    TextField input_role;

    @FXML
    Button buttonAddNew;
    public void buttonAddNew() throws SQLException {
        String val_fullname = input_fullname.getText().trim();
        String val_phone = input_phone.getText().trim();
        String val_email = input_email.getText().trim();
        String val_address = input_address.getText().trim();
        LocalDate val_dob = input_dob.getValue();
        LocalDate val_joindate = input_joindate.getValue();
        String val_account = input_account.getText().trim().toLowerCase();
        String val_password = input_password.getText().trim();
        String val_role = input_role.getText().trim();

        if(val_fullname.isEmpty()){
            return;
        }

        if(Integer.parseInt(val_role) == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Staff cannot set role 1");
            alert.show();
            return;
        } else if (Staff.checkDuplicate(val_account)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("The account is already on the system ! ");
            alert.show();
            return;
        } else {
            Staff.addStaff(val_account,val_password,val_fullname,val_address,val_email,val_phone, Integer.parseInt(val_role),val_dob,val_joindate);
            input_fullname.setText("");
            input_phone.setText("");
            input_email.setText("");
            input_address.setText("");
            input_dob.setValue(null);
            input_joindate.setValue(null);
            input_account.setText("");
            input_password.setText("");
            input_role.setText("");
        }

        table();
    }

    @FXML
    Button buttonUpdate;
    public void buttonUpdate() throws SQLException {
        myIndex = table_staff.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table_staff.getItems().get(myIndex).getId()));

        String val_fullname = input_fullname.getText().trim();
        String val_phone = input_phone.getText().trim();
        String val_email = input_email.getText().trim();
        String val_address = input_address.getText().trim();
        LocalDate val_dob = input_dob.getValue();
        LocalDate val_joindate = input_joindate.getValue();
        String val_account = input_account.getText().trim().toLowerCase();
        String val_password = input_password.getText().trim();
        String val_role = input_role.getText().trim();
        LocalDate val_enddate = input_enddate.getValue();

        if(val_fullname.isEmpty()){
            return;
        }

        if(Integer.parseInt(val_role) == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Staff cannot set role 1");
            alert.show();
            return;
        }else {
            Staff.updateStaff(id,val_account,val_password,val_fullname,val_address,val_email,val_phone, Integer.parseInt(val_role),val_dob,val_joindate,val_enddate);
            input_fullname.setText("");
            input_phone.setText("");
            input_email.setText("");
            input_address.setText("");
            input_dob.setValue(null);
            input_joindate.setValue(null);
            input_account.setText("");
            input_password.setText("");
            input_role.setText("");
        }

        table();
    }

    int id ;

    int myIndex ;

    public void table(){
        ObservableList<Staff> staffs = Staff.getAllStaffs();
        table_staff.setItems(staffs);
        column_fullname.setCellValueFactory(f -> f.getValue().fullNameProperty());
        column_dob.setCellValueFactory(f -> f.getValue().dobProperty().asString());
        column_phone.setCellValueFactory(f -> f.getValue().phoneProperty());
        column_email.setCellValueFactory(f -> f.getValue().emailProperty());
        column_address.setCellValueFactory(f -> f.getValue().addressProperty());
        column_role.setCellValueFactory(f -> f.getValue().roleProperty());
        column_joindate.setCellValueFactory(f -> f.getValue().joinDateProperty());
        column_enddate.setCellValueFactory(f -> f.getValue().endDateProperty());

        input_enddate.setDisable(true);
        buttonUpdate.setDisable(true);

        table_staff.setRowFactory(it -> {
            TableRow<Staff> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table_staff.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table_staff.getItems().get(myIndex).getId()));
                    input_fullname.setText(table_staff
                            .getItems()
                            .get(myIndex)
                            .getFullname());
                    input_phone.setText(table_staff
                            .getItems()
                            .get(myIndex)
                            .getPhone());
                    input_email.setText(table_staff
                            .getItems()
                            .get(myIndex)
                            .getPhone());
                    input_address.setText(table_staff
                            .getItems()
                            .get(myIndex)
                            .getAddress());
                    input_dob.setValue(table_staff
                            .getItems()
                            .get(myIndex)
                            .getDob());
                    input_joindate.setValue(table_staff
                            .getItems()
                            .get(myIndex)
                            .getJoinDate());
                    input_enddate.setValue(table_staff
                            .getItems()
                            .get(myIndex)
                            .getEndDate());
                    input_account.setText(table_staff
                            .getItems()
                            .get(myIndex)
                            .getAccount());
                    input_password.setText(table_staff
                            .getItems()
                            .get(myIndex)
                            .getPassword());
                    input_role.setText(table_staff
                            .getItems()
                            .get(myIndex)
                            .getRole());

                    input_enddate.setDisable(false);
                    buttonUpdate.setDisable(false);
                    buttonAddNew.setDisable(true);
                }
            });
            return myRow;
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
    }
}
