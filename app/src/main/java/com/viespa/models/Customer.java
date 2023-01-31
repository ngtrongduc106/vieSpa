package com.viespa.models;

import com.viespa.utils.DBUtil;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Customer {
    private final SimpleLongProperty id = new SimpleLongProperty();
    private final SimpleStringProperty fullName = new SimpleStringProperty();
    private final SimpleStringProperty is_female = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> dob = new SimpleObjectProperty<>();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();

    public static ObservableList<Customer> getAllCustomers() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            statement = connection.prepareStatement("SELECT * from customers");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer it = new Customer();
                it.setId(Long.valueOf(resultSet.getString("id")));
                it.setFullName(resultSet.getString("fullname"));
                it.setIs_female(resultSet.getString("is_female"));
                it.setDob(LocalDate.parse(resultSet.getString("dob")));
                it.setPhone(resultSet.getString("phone"));
                it.setEmail(resultSet.getString("email"));
                it.setAddress(resultSet.getString("address"));
                customers.add(it);
            }

            return customers;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, statement, resultSet);
        }
    }

    public static void addNewCustomer(String fullName, String phone, String email, String address, String is_female, LocalDate dob) throws SQLException {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "INSERT into " +
                            "customers(fullname, phone, address, email,dob,is_female) " +
                            "values (?,?,?,?,?,?)");

            statement.setString(1, fullName);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, email);
            statement.setString(5, String.valueOf(dob));
            statement.setString(6, is_female);

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            db.closeAll(connection, statement, null);
        }
    }

    public static void updateCustomer(
            String fullName,
            String phone,
            String email,
            String address,
            int id,
            String is_female,
            LocalDate dob
    ) throws SQLException {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "UPDATE customers" +
                            " SET fullname = ? , phone = ? , email = ? , address = ? , dob = ? , is_female = ?" +
                            " where id = ?");


            statement.setString(1, fullName);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, address);
            statement.setString(5, String.valueOf(dob));
            statement.setString(6, is_female);
            statement.setString(7, String.valueOf(id));

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
            statement = connection.prepareStatement("SELECT id from customers where fullname = ?");
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

    //by Name
    public static Customer getByName(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * from customers where fullname = ?");
            statement.setString(1, data);
            resultSet = statement.executeQuery();
            Customer it = new Customer();
            if (resultSet.next()) {
                it.setId(Long.valueOf(resultSet.getString("id")));
                it.setFullName(resultSet.getString("fullname"));
                it.setIs_female(resultSet.getString("is_female"));
                it.setDob(LocalDate.parse(resultSet.getString("dob")));
                it.setPhone(resultSet.getString("phone"));
                it.setEmail(resultSet.getString("email"));
                it.setAddress(resultSet.getString("address"));
            }
            return it;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.closeAll(connection, statement, resultSet);
        }
    }

    public static ObservableList<Customer> search(String text) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            statement = connection.prepareStatement("SELECT * from customers WHERE customers.fullname like '%"+ text+"%' or customers.email like '%\"+ text+\"%' or customers.address like '%\"+ text+\"%' or customers.phone like '%\"+ text+\"%'");

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer it = new Customer();
                it.setId(Long.valueOf(resultSet.getString("id")));
                it.setFullName(resultSet.getString("fullname"));
                it.setIs_female(resultSet.getString("is_female"));
                it.setDob(LocalDate.parse(resultSet.getString("dob")));
                it.setPhone(resultSet.getString("phone"));
                it.setEmail(resultSet.getString("email"));
                it.setAddress(resultSet.getString("address"));
                customers.add(it);
            }

            return customers;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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

    public void setId(Long newId) {
        id.set(newId);
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String newFullName) {
        fullName.set(newFullName);
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String newPhone) {
        phone.set(newPhone);
    }

    public SimpleStringProperty isFemeleProperty() {
        return is_female;
    }

    public String getIs_female() {
        return is_female.get();
    }

    public void setIs_female(String newIsFemale) {
        is_female.set(newIsFemale);
    }

    public SimpleObjectProperty<LocalDate> dobProperty() {
        return dob;
    }

    public LocalDate getDob() {
        return dob.get();
    }

    public void setDob(LocalDate newDob) {
        dob.set(newDob);
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String newAddress) {
        address.set(newAddress);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String newEmail) {
        email.set(newEmail);
    }
}
