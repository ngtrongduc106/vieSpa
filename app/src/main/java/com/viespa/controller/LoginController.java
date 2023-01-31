package com.viespa.controller;

import com.viespa.App;
import com.viespa.models.User;
import com.viespa.utils.AlertUtil;
import com.viespa.utils.DBUtil;
import com.viespa.utils.MD5Util;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {
    @FXML
    TextField input_account;

    @FXML
    PasswordField input_password;

    @FXML
    Label error_account;

    @FXML
    Label error_password;

    User user = User.getInstance();

    @FXML
    public void buttonLogin() throws IOException {
        String val_account = input_account.getText().trim().toLowerCase();
        String val_password = input_password.getText().trim();
        int errors = 0;

        // Validation account name
        if (val_account.isEmpty()) {
            error_account.setText("Account name cannot leave empty !");
            errors = 1;
        } else if (checkWhiteSpace(val_account)) {
            error_account.setText("Account name cannot has white space !");
            errors = 1;
        } else {
            error_account.setText("");
        }

        // Validate password
        if (val_password.isEmpty()) {
            error_password.setText("Password cannot leave empty !");
            errors = 1;
        } else {
            error_password.setText("");
        }
        String md5_password = MD5Util.getMD5(val_password);
        //Check DB
        if (errors == 0) {
            DBUtil db = new DBUtil();
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                connection = db.connect();
                statement = connection.prepareStatement("SELECT * FROM users WHERE account = ? AND password = ? ");

                statement.setString(1, val_account);
                statement.setString(2, md5_password);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    if (!resultSet.getString("status").equals("1")) {
                        user.setId(Integer.parseInt(resultSet.getString("id")));
                        user.setAccount(resultSet.getString("account"));
                        user.setFullname(resultSet.getString("fullname"));
                        user.setRole(Integer.parseInt(resultSet.getString("role")));
                        user.setEmail(resultSet.getString("email"));
                        user.setDob(LocalDate.parse(resultSet.getString("dob")));
                        user.setAddress(resultSet.getString("address"));
                        App.setRoot("views/home-view");
                    } else {
                        AlertUtil.showError("You do not hava authority");
                    }
                } else {
                    AlertUtil.showError("Username or password incorrect !");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                db.closeAll(connection, statement, resultSet);
            }
        }

    }

    public void resetPassword() throws IOException {
        App.setRoot("views/reset-view");
    }

    public boolean checkWhiteSpace(String data) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }
}
