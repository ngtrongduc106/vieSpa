package com.viespa.models;

import com.viespa.utils.DBUtil;
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

    private final SimpleStringProperty active = new SimpleStringProperty();

    public static ObservableList<Course> getAllCourses() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("SELECT course.id , course.name , course.price , course.description, course.active FROM course");
            rs = pst.executeQuery();
            while (rs.next()) {
                Course it = new Course();
                it.setId(Long.parseLong(rs.getString("id")));
                it.setName(rs.getString("name"));
                it.setPrice(rs.getString("price"));
                it.setDescription(rs.getString("description"));
                it.setActive(rs.getString("active"));
                courses.add(it);
            }
            return courses;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }

    public static ObservableList<Course> getActiveCourses() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("SELECT course.id , course.name , course.price , course.description, course.active FROM course WHERE active");
            rs = pst.executeQuery();
            while (rs.next()) {
                Course it = new Course();
                it.setId(Long.parseLong(rs.getString("id")));
                it.setName(rs.getString("name"));
                it.setPrice(rs.getString("price"));
                it.setDescription(rs.getString("description"));
                it.setActive(rs.getString("active"));
                courses.add(it);
            }
            return courses;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }

    public static void addNew(
            String name,
            String price,
            String description,
            String active
    ) throws SQLException {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "INSERT into " +
                            "course(name, price, description, active) " +
                            "values (?,?,?,?)");

            statement.setString(1, name);
            statement.setString(2, price);
            statement.setString(3, description);
            statement.setString(4, active);

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            db.closeAll(connection, statement, null);
        }
    }

    public static void update(
            String id,
            String name,
            String price,
            String description,
            String active
    ) throws SQLException {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "UPDATE course" +
                            " SET name = ? , price = ? , description = ?, active = ?" +
                            " where id = ?");


            statement.setString(1, name);
            statement.setString(2, price);
            statement.setString(3, description);
            statement.setString(4, active);
            statement.setString(5, id);

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            db.closeAll(connection, statement, null);
        }
    }

    public static String queryId(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT id from course where name = ?");
            statement.setString(1, data);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.closeAll(connection, statement, resultSet);
        }
    }

    public static String queryPrice(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT price from course where id = ?");
            statement.setString(1, data);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("price");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.closeAll(connection, statement, resultSet);
        }
    }

    public static Course getByName(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * from course where name = ?");
            statement.setString(1, data);
            resultSet = statement.executeQuery();
            Course it = new Course();
            if (resultSet.next()) {
                it.setId(Long.parseLong(resultSet.getString("id")));
                it.setName(resultSet.getString("name"));
                it.setPrice(resultSet.getString("price"));
                it.setDescription(resultSet.getString("description"));
                it.setActive(resultSet.getString("active"));
            }
            return it;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.closeAll(connection, statement, resultSet);
        }
    }

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


    public String getActive() {
        return active.get();
    }

    public SimpleStringProperty activeProperty() {
        return active;
    }

    public void setActive(String newActive) {
        active.set(newActive);
    }
}
