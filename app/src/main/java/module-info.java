module com.viespa {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.viespa to javafx.fxml;
    exports com.viespa;
}
