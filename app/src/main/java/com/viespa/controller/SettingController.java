package com.viespa.controller;

import com.viespa.models.Role;
import com.viespa.models.User;
import com.viespa.utils.AlertUtil;
import com.viespa.utils.DButil;
import com.viespa.utils.Md5;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    User user = User.getInstance();
    @FXML
    Label lb_name;

    @FXML
    Label lb_username;

    @FXML
    Label lb_role;

    @FXML
    Label lb_email;

    @FXML
    Label lb_dob;

    @FXML
    Label lb_address;

    @FXML
    PasswordField old_pass;

    @FXML
    PasswordField new_pass;

    @FXML
    PasswordField conf_pass;

    public void change_pass() {
        String val_old_pass = old_pass.getText();
        String val_new_pass = new_pass.getText();
        String val_conf_pass = conf_pass.getText();

        if (val_old_pass.equals("") || val_new_pass.equals("") || val_conf_pass.equals("")) {
            AlertUtil.showError("Input can not empty for this request");
        } else if (!val_new_pass.equals(val_conf_pass)) {
            AlertUtil.showError("Wrong confirmation password !");
        } else {
            DButil db = new DButil();
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                connection = db.connect();
                statement = connection.prepareStatement("UPDATE users SET password = ? WHERE password = ? ");

                statement.setString(1, Md5.getMD5(val_new_pass));
                statement.setString(2, Md5.getMD5(val_old_pass));

                int countRowModified = statement.executeUpdate();

                if (countRowModified > 1) {
                    AlertUtil.showSuccess("New password has been saved");
                    old_pass.setText("");
                    new_pass.setText("");
                    conf_pass.setText("");
                } else {
                    AlertUtil.showError("Old password invalid !");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                db.closeAll(connection, statement, resultSet);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lb_name.setText("Full name : " + user.getFullname());
        lb_username.setText("Username : " + user.getAccount());
        lb_role.setText("Role : " + Role.queryRoleName(String.valueOf(user.getRole())));
        lb_email.setText("Email: " + user.getEmail());
        lb_dob.setText("Email: " + user.getDob());
        lb_address.setText("Email: " + user.getAddress());
    }
}
