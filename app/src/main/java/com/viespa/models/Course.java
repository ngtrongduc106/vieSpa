package com.viespa.models;

import com.viespa.utils.DButil;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
    private final SimpleLongProperty id = new SimpleLongProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty price = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleStringProperty createBy = new SimpleStringProperty();

    public SimpleLongProperty idProperty() {
        return id;
    }

    public long getId() {
        return id.get();
    }

    public void setId(long newId) {
        id.set(newId);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        name.set(newName);
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String newPrice) {
        price.set(newPrice);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String newDescription) {
        description.set(newDescription);
    }

    public SimpleStringProperty createByProperty() {
        return createBy;
    }

    public String getCreateBy() {
        return createBy.get();
    }

    public void setCreateBy(String newCreateBy) {
        createBy.set(newCreateBy);
    }

    public static ObservableList<Course> getAllCourses() {
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("SELECT courses.id , courses.name , courses.price , courses.description , users.fullname as create_by FROM courses LEFT JOIN users on courses.create_by = users.id");
            rs = pst.executeQuery();
            while (rs.next()) {
                Course it = new Course();
                it.setId(Long.parseLong(rs.getString("id")));
                it.setName(rs.getString("name"));
                it.setPrice(rs.getString("price"));
                it.setDescription(rs.getString("description"));
                it.setCreateBy(rs.getString("create_by"));
            }

            return courses;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }
}
