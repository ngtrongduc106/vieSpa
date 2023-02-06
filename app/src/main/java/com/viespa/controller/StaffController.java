package com.viespa.controller;

import com.viespa.models.Role;
import com.viespa.models.Staff;
import com.viespa.models.User;
import com.viespa.utils.AlertUtil;
import com.viespa.utils.DateUtil;
import com.viespa.utils.MD5Util;
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

public class StaffController implements Initializable {

    private final String default_password = MD5Util.getMD5("123123");
    @FXML
    TableView<Staff> table_staff;

    @FXML
    TableColumn<Staff, String> column_account;

    @FXML
    TableColumn<Staff, String> column_fullname;

    @FXML
    TableColumn<Staff, String> column_phone;

    @FXML
    TableColumn<Staff, String> column_address;

    @FXML
    TableColumn<Staff, String> column_role;

    @FXML
    TableColumn<Staff, String> column_joindate;

    @FXML
    TableColumn<Staff, String> column_enddate;

    @FXML
    private TableColumn<Staff, String> column_status;

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
    ChoiceBox<String> input_role;

    @FXML
    ChoiceBox<String> input_status;

    @FXML
    Button buttonAddNew;
    int id;
    int myIndex;

    String phoneRegex = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonUpdate;

    @FXML
    void buttonCancel() {
        input_fullname.setText("");
        input_phone.setText("");
        input_email.setText("");
        input_address.setText("");
        input_dob.setValue(null);
        input_joindate.setValue(null);
        input_account.setText("");
        input_role.setValue("");
        input_status.setValue("");
        buttonUpdate.setDisable(true);
        buttonAddNew.setDisable(false);
        input_enddate.setDisable(true);
        table_staff.getSelectionModel().select(null);
    }

    public void buttonAddNew() throws SQLException {
        String val_fullname = input_fullname.getText().trim();
        String val_phone = RegexUtil.validate(input_phone.getText().trim(), phoneRegex);
        String val_email = RegexUtil.validate(input_email.getText().trim(), emailRegex);
        String val_address = input_address.getText().trim();
        LocalDate val_dob = input_dob.getValue();
        LocalDate val_joindate = input_joindate.getValue();
        String val_account = input_account.getText().trim().toLowerCase();
        String val_role = Role.queryRoleId(input_role.getValue());
        String val_status = input_status.getValue().equals("Active") ? "0" : "1";

        if (val_phone.equals("0")) {
            AlertUtil.showError("Phone wrong format !");
            return;
        }

        if (val_email.equals("0")) {
            AlertUtil.showError("Incorrect email format, e.g: test@mail.com");
            return;
        }

        if (val_fullname.isEmpty() || val_phone.isEmpty() || val_email.isEmpty() || val_address.isEmpty()
                || val_dob == null || val_joindate == null || val_account.isEmpty() || val_role == null) {
            AlertUtil.showError("Inputs cannot leave empty");
            return;
        }

        if (Staff.checkDuplicate(val_account)) {
            AlertUtil.showError("This account is existed, try a different one! ");
            return;
        }

        if(User.getInstance().getRole() != 1) {
            AlertUtil.showError("You do not have the authority to add staff");
            return;
        }

        Staff.addStaff(val_account, default_password, val_fullname, val_address, val_email, val_phone,
                Integer.parseInt(val_role), val_dob, val_joindate, val_status);
        input_fullname.setText("");
        input_phone.setText("");
        input_email.setText("");
        input_address.setText("");
        input_dob.setValue(null);
        input_joindate.setValue(null);
        input_account.setText("");
        input_role.setValue("");
        input_status.setValue("");
        AlertUtil.showSuccess("Staff created successfully ! Default password : 123123");

        table();
    }

    public void buttonUpdate() throws SQLException {
        myIndex = table_staff.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table_staff.getItems().get(myIndex).getId()));

        String val_fullname = input_fullname.getText().trim();
        String val_phone = RegexUtil.validate(input_phone.getText().trim(), phoneRegex);
        String val_email = RegexUtil.validate(input_email.getText().trim(), emailRegex);
        String val_address = input_address.getText().trim();
        LocalDate val_dob = input_dob.getValue();
        LocalDate val_joindate = input_joindate.getValue();
        String val_account = input_account.getText().trim().toLowerCase();
        String val_role = Role.queryRoleId(input_role.getValue());
        LocalDate val_enddate = input_enddate.getValue();
        String val_status = input_status.getValue().equals("Active") ? "0" : "1";

        if (val_phone.equals("0")) {
            AlertUtil.showError("Incorrect phone format !");
            return;
        }

        if (val_email.equals("0")) {
            AlertUtil.showError("Incorrect email format, e.g: test@mail.com");
            return;
        }

        if (val_fullname.isEmpty() || val_phone.isEmpty() || val_email.isEmpty() || val_address.isEmpty()
                || val_dob == null || val_joindate == null || val_account.isEmpty() || val_role == null) {
            AlertUtil.showError("Inputs cannot leave empty!");
            return;
        }

        if(User.getInstance().getRole() != 1) {
            AlertUtil.showError("You do not have the authority to edit staff");
            return;
        }

        Staff.updateStaff(id, val_account, val_fullname, val_address, val_email, val_phone, Integer.parseInt(val_role),
                val_dob, val_joindate, val_enddate, val_status);
        input_fullname.setText("");
        input_phone.setText("");
        input_email.setText("");
        input_address.setText("");
        input_dob.setValue(null);
        input_joindate.setValue(null);
        input_account.setText("");
        input_role.setValue("");
        input_status.setValue("");

        table();

    }

    public void table() {
        ObservableList<Staff> staffs = Staff.getAllStaffs();
        table_staff.setItems(staffs);
        column_account.setCellValueFactory(f -> f.getValue().accountProperty());
        column_fullname.setCellValueFactory(f -> f.getValue().fullNameProperty());
        column_phone.setCellValueFactory(f -> f.getValue().phoneProperty());
        column_address.setCellValueFactory(f -> f.getValue().addressProperty());
        column_role.setCellValueFactory(f -> f.getValue().roleProperty());
        column_joindate
                .setCellValueFactory(f -> DateUtil.convert(String.valueOf(f.getValue().joinDateProperty().getValue())));
        column_enddate.setCellValueFactory(f -> f.getValue().endDateProperty().getValue() != null
                ? DateUtil.convert(String.valueOf(f.getValue().endDateProperty().getValue()))
                : new SimpleStringProperty(""));
        column_status.setCellValueFactory(f -> f.getValue().statusProperty().getValue().equals("0")
                ? new SimpleStringProperty("Active")
                : new SimpleStringProperty("Inactive"));

        input_enddate.setDisable(true);
        buttonUpdate.setDisable(true);
        buttonAddNew.setDisable(false);

        table_staff.setRowFactory(it -> {
            TableRow<Staff> myRow = new TableRow<>();

            myRow.setOnMouseClicked(event -> {
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
                            .getEmail());
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
                    input_role.setValue(table_staff
                            .getItems()
                            .get(myIndex)
                            .getRole());
                    input_status.setValue(table_staff.getItems().get(myIndex).getStatus().equals("0") ? "Active" : "Inactive");

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
        ObservableList<Role> roles = Role.getAllRole();
        assert roles != null;
        roles.stream().map(Role::getRole).forEach(t -> input_role.getItems().add(t));

        input_status.getItems().add("Active");
        input_status.setValue("Active");
        input_status.getItems().add("Inactive");
    }
}
