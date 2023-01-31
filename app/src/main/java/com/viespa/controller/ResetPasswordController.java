package com.viespa.controller;

import com.viespa.App;
import com.viespa.utils.AlertUtil;
import com.viespa.utils.DBUtil;
import com.viespa.utils.MD5Util;
import com.viespa.utils.RandomString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
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

    private final String accountAdminMail = "duc.nt.2081@aptechlearning.edu.vn" ;
    private final String passAdminMail = "hdegofgkoynycuqq" ;
    private final String passReset = RandomString.getAlphaNumericString(10);

    final String subject = "Reset password";

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

//        Check DB
        if (errors == 0) {
            DBUtil db = new DBUtil();
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                connection = db.connect();
                statement = connection.prepareStatement("UPDATE users SET password = ? WHERE account = ? AND email = ? ");

                statement.setString(1, MD5Util.getMD5(passReset));
                statement.setString(2, val_account);
                statement.setString(3, val_email);

                int countRowModified = statement.executeUpdate();

                if (countRowModified > 0) {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
                    props.put("mail.smtp.port", "587"); //TLS Port
                    props.put("mail.smtp.auth", "true"); //enable authentication
                    props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
                    Authenticator auth = new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(accountAdminMail, passAdminMail);
                        }
                    };
                    Session session = Session.getInstance(props, auth);
                    MimeMessage msg = new MimeMessage(session);
                    //set message headers
                    msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                    msg.addHeader("format", "flowed");
                    msg.addHeader("Content-Transfer-Encoding", "8bit");
                    msg.setFrom(new InternetAddress(accountAdminMail, "NoReply-JD"));
                    msg.setReplyTo(InternetAddress.parse(accountAdminMail, false));
                    msg.setSubject(subject, "UTF-8");
                    String bodyMail = "New your password : " + passReset;
                    msg.setText(bodyMail, "UTF-8");
                    msg.setSentDate(new Date());
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(val_email, false));
                    Transport.send(msg);
                    AlertUtil.showSuccess("Reset password successfully ! Please check your email");
                    App.setRoot("views/login-view");
                } else {
                    AlertUtil.showError("Account or email incorrect !");
                }

            } catch (SQLException | MessagingException | IOException e) {
                throw new RuntimeException(e);
            } finally {
                db.closeAll(connection, statement, resultSet);
            }
        }
    }

    public boolean checkWhiteSpace(String data) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }
}
