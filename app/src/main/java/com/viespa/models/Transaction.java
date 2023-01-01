package com.viespa.models;

import com.viespa.utils.DButil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class Transaction {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty customer = new SimpleStringProperty();
    private final SimpleStringProperty course = new SimpleStringProperty();
    private final SimpleStringProperty staff = new SimpleStringProperty();
    private final SimpleStringProperty pay = new SimpleStringProperty();
    private final SimpleStringProperty note = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> booking = new SimpleObjectProperty<>();
    private final SimpleStringProperty createBy = new SimpleStringProperty();

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String newId){
        id.set(newId);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String newCustomer) {
        customer.set(newCustomer);
    }

    public String getCustomer() {
        return customer.get();
    }

    public SimpleStringProperty courseProperty() {
        return course;
    }

    public void setCourse(String newCourse) {
        course.set(newCourse);
    }

    public String getCourse() {
        return course.get();
    }

    public SimpleStringProperty staffProperty() {
        return staff;
    }

    public void setStaff(String newStaff) {
        staff.set(newStaff);
    }

    public String getStaff() {
        return staff.get();
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String newNote) {
        note.set(newNote);
    }

    public String getNote() {
        return note.get();
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

    public String getPay() {
        return pay.get();
    }

    public SimpleObjectProperty<LocalDate> bookingProperty() {
        return booking;
    }
    public void setBooking(LocalDate newBooking){
        booking.set(newBooking);
    }

    public LocalDate getBooking() {
        return booking.get();
    }

    public static ObservableList<Transaction> getAllTransaction (){
       DButil db = new DButil();
       Connection connection = db.connect();
       PreparedStatement pst = null;
       ResultSet rs = null;
       ObservableList<Transaction> transactions = FXCollections.observableArrayList();
       try {
           pst = connection.prepareStatement("SELECT transactions.id, customers.fullname as customer , course.name as course , u1.fullname as staff_sup , transactions.pay , transactions.note , transactions.booking , u2.fullname as create_by\n" +
                   "FROM `transactions` \n" +
                   "JOIN customers on customers.id = transactions.customer_id\n" +
                   "JOIN course on course.id = transactions.course_id\n" +
                   "JOIN users u1 on u1.id = transactions.staff_id\n" +
                   "JOIN users u2 on u2.id = transactions.created_by");
           rs = pst.executeQuery();
           while (rs.next()) {
               Transaction it = new Transaction();
               it.setId(rs.getString("id"));
               it.setCustomer(rs.getString("customer"));
               it.setCourse(rs.getString("course"));
               it.setStaff(rs.getString("staff_sup"));
               it.setPay(rs.getString("pay"));
               it.setNote(rs.getString("note"));
               it.setBooking(LocalDate.parse(rs.getString("booking")));
               it.setCreateBy(rs.getString("create_by"));
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

    public static void addNew(
            String customer_id,
            String course_id,
            String staff_id,
            String pay,
            String note,
            LocalDate booking,
            String create_by
    ) throws SQLException {
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "INSERT into " +
                            "transactions(customer_id , course_id , staff_id , pay , note , booking , created_by ) " +
                            "values (?,?,?,?,?,?,?)");
            statement.setString(1,customer_id);
            statement.setString(2,course_id);
            statement.setString(3,staff_id);
            statement.setString(4,pay);
            statement.setString(5,note);
            statement.setDate(6, Date.valueOf(booking));
            statement.setString(7,create_by);

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
            String customer_id,
            String course_id,
            String staff_id,
            String pay,
            String note,
            LocalDate booking,
            String id
    ) throws SQLException {
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE transactions set customer_id = ? , course_id = ? , staff_id = ? , pay = ? , note = ? , booking = ? where id = ?");

            statement.setString(1, customer_id);
            statement.setString(2,course_id);
            statement.setString(3,staff_id);
            statement.setString(4,pay);
            statement.setString(5,note);
            statement.setString(6, String.valueOf(booking));
            statement.setString(7,id);

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

