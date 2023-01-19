module com.viespa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires org.jetbrains.annotations;
    requires javafx.graphics;
    requires javax.mail;

    opens com.viespa to javafx.fxml;
    exports com.viespa;
    opens com.viespa.controller to javafx.fxml;
}
