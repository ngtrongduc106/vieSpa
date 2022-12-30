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

public class Customer {
    private final SimpleLongProperty id = new SimpleLongProperty() ;
    private final SimpleStringProperty fullName = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();

    public SimpleLongProperty idProperty(){return id;}

    public long getId() {
        return id.get();
    }

    public void setId(Long newId){
        id.set(newId);
    }

    public SimpleStringProperty fullNameProperty(){return fullName;}

    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String newFullName){
        fullName.set(newFullName);
    }

    public SimpleStringProperty phoneProperty() {return phone;}

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String newPhone){
        phone.set(newPhone);
    }

    public SimpleStringProperty addressProperty() {return address;}

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String newAddress){
        address.set(newAddress);
    }

    public SimpleStringProperty emailProperty(){return email;}

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String newEmail){
        email.set(newEmail);
    }

    public static ObservableList<Customer> getAllCustomers() {
            DButil db = new DButil();
            Connection connection = db.connect();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            ObservableList<Customer> customers = FXCollections.observableArrayList();
            try {
                statement = connection.prepareStatement("SELECT * from customer");
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Customer it = new Customer();
                    it.setId(Long.valueOf(resultSet.getString("id")));
                    it.setFullName(resultSet.getString("name"));
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

    public static void addNewCustomer(String fullName , String phone , String email , String address) throws SQLException {
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "INSERT into " +
                            "customer(name, phone, address, email) " +
                            "values (?,?,?,?)");

            statement.setString(1, fullName);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, email);

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }finally {
            db.closeAll(connection, statement, null);
        }
    }

    public static void updateCustomer(
            String fullName,
            String phone,
            String email,
            String address,
            int id
    ) throws SQLException {
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "UPDATE customer SET name = ? , phone = ? , email = ? , address = ? where id = ?");


            statement.setString(1, fullName);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, address);
            statement.setString(5, String.valueOf(id));

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            db.closeAll(connection, statement, null);
        }
    }
}
