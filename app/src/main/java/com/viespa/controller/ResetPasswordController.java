package com.viespa.controller;

import com.viespa.App;
import com.viespa.utils.DButil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordController implements Initializable {

    @FXML
    private Label error_account;

    @FXML
    private Label error_email;

    @FXML
    private TextField input_account;

    @FXML
    private TextField input_email;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backToLogin(ActionEvent actionEvent) throws IOException {
        App.setRoot("views/login-view");
    }

    public void buttonConfirm(ActionEvent actionEvent) {
        String val_account = input_account.getText().trim().toLowerCase();
        String val_email = input_email.getText().trim();
        int errors = 0;

        // Validation account name
        if (val_account.isEmpty()) {
            error_account.setText("Account name cannot empty !");
            errors = 1;
        } else if (checkWhiteSpace(val_account)) {
            error_account.setText("Account name cannot has white space !");
            errors = 1;
        } else {
            error_account.setText("");
        }

        // Validate password
        if (val_email.isEmpty()) {
            error_email.setText("Email cannot empty !");
            errors = 1;
        } else {
            error_email.setText("");
        }

        //Check DB
        if (errors == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Reset password successfully !");
            alert.show();
        }
    }

    public boolean checkWhiteSpace(String data) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }
}
