package com.viespa.models;

import com.viespa.utils.DButil;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Customer {
    private SimpleLongProperty id = new SimpleLongProperty() ;
    private SimpleStringProperty fullName = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();
    private SimpleStringProperty address = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDate> dob = new SimpleObjectProperty<>();

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

    public SimpleObjectProperty<LocalDate> dobProperty(){return dob;}

    public LocalDate getDob() {
        return dob.get();
    }

    public void setDob(LocalDate newDob){
        dob.set(newDob);
    }

    public static ObservableList<Customer> getAllCustomers() {
            DButil db = new DButil();
            Connection connection = db.connect();
            PreparedStatement pst = null;
            ResultSet rs = null;
            ObservableList<Customer> customers = FXCollections.observableArrayList();
            try {
                pst = connection.prepareStatement("select * from customers");
                rs = pst.executeQuery();
                while (rs.next()) {
                    Customer it = new Customer();
                    it.setId(Long.valueOf(rs.getString("id")));
                    it.setFullName(rs.getString("fullname"));
                    it.setPhone(rs.getString("phone"));
                    it.setEmail(rs.getString("email"));
                    it.setDob((rs.getObject("dob", LocalDate.class)));
                    it.setAddress(rs.getString("address"));
                    customers.add(it);
                }

                return customers;

            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                db.closeAll(connection, pst, rs);
            }
        }

    public static void addNewCustomer(String fullName , String phone , String email , String address , LocalDate dob) throws SQLException {
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "INSERT into " +
                            "customers(fullname, phone, address, email, dob) " +
                            "values (?,?,?,?,?)");

            statement.setString(1, fullName);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, email);
            statement.setDate(5, Date.valueOf(dob));

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
            LocalDate dob,
            int id
    ) throws SQLException {
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "UPDATE customers SET fullname = ? , phone = ? , email = ? , dob = ? , address = ? where id = ?");


            statement.setString(1, fullName);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setDate(4, Date.valueOf(dob));
            statement.setString(5, address);
            statement.setString(6, String.valueOf(id));

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
