package com.viespa.models;

import com.viespa.utils.DButil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Transaction {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty customer = new SimpleStringProperty();
    private final SimpleStringProperty course = new SimpleStringProperty();
    private final SimpleStringProperty staff = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> booking = new SimpleObjectProperty<>();
    private final SimpleStringProperty note = new SimpleStringProperty();
    private final SimpleStringProperty createBy = new SimpleStringProperty();

    private final SimpleStringProperty pay = new SimpleStringProperty();

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String newId){
        id.set(newId);
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String newCustomer) {
        customer.set(newCustomer);
    }

    public SimpleStringProperty courseProperty() {
        return course;
    }

    public void setCourse(String newCourse) {
        course.set(newCourse);
    }

    public SimpleStringProperty staffProperty() {
        return staff;
    }

    public void setStaff(String newStaff) {
        staff.set(newStaff);
    }

    public SimpleObjectProperty<LocalDate> bookingProperty() {
        return booking;
    }

    public void setBooking(LocalDate newBooking) {
        booking.set(newBooking);
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String newNote) {
        note.set(newNote);
    }

    public SimpleStringProperty createByProperty() {
        return createBy;
    }

    public void setCreateBy(String newCreateBy) {
        createBy.set(newCreateBy);
    }

    public SimpleStringProperty payProperty() {
        return pay;
    }

    public void setPay(String newPay){
        pay.set(newPay);
    }

    public static ObservableList<Transaction> getAllTransaction (){
       DButil db = new DButil();
       Connection connection = db.connect();
       PreparedStatement pst = null;
       ResultSet rs = null;
       ObservableList<Transaction> transactions = FXCollections.observableArrayList();
       try {
           pst = connection.prepareStatement("SELECT transactions.id , customers.fullname as customer_name , courses.name as course , u1.fullname as staff_name , transactions.booking_at , transactions.transaction_note , u2.fullname as createby , courses.price as pay FROM transactions \n" +
                   "JOIN customers ON customers.id = transactions.customer_id\n" +
                   "JOIN users u1 ON u1.id = transactions.staff_id\n" +
                   "JOIN courses on courses.id = transactions.course_id\n" +
                   "JOIN users u2 on u2.id = transactions.create_by");
           rs = pst.executeQuery();
           while (rs.next()) {
               Transaction it = new Transaction();
               it.setId(rs.getString("id"));
               it.setCustomer(rs.getString("customer_name"));
               it.setCourse(rs.getString("course"));
               it.setStaff(rs.getString("staff_name"));
               it.setBooking((rs.getObject("booking_at", LocalDate.class)));
               it.setNote(rs.getString("transaction_note"));
               it.setCreateBy(rs.getString("createby"));
               it.setPay(rs.getString("pay"));

               transactions.add(it);
           }

           return transactions;

       } catch (SQLException ex) {
           ex.printStackTrace();
           return null;
       } finally {
           db.closeAll(connection, pst, rs);
       }
   }
}

