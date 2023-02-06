package com.viespa.models;

import com.viespa.utils.DBUtil;
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

public class Transaction {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty customer = new SimpleStringProperty();
    private final SimpleStringProperty course = new SimpleStringProperty();
    private final SimpleStringProperty staff = new SimpleStringProperty();

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    private int staff_id;
    private final SimpleStringProperty pay = new SimpleStringProperty();
    private final SimpleStringProperty note = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> booking = new SimpleObjectProperty<>();
    private final SimpleStringProperty createBy = new SimpleStringProperty();

    public Transaction() {
    }

    public static ObservableList<Transaction> getAllTransaction() {
        DBUtil db = new DBUtil();
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
                    "JOIN users u2 on u2.id = transactions.created_by\n" +
                    "ORDER BY id ASC");
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

    public static Transaction getLastInput() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Transaction transaction = new Transaction();
        try {
            pst = connection.prepareStatement("SELECT transactions.id, customers.fullname as customer , course.name as course , u1.fullname as staff_sup , transactions.pay , transactions.note , transactions.booking , u2.fullname as create_by\n" +
                    "FROM `transactions` \n" +
                    "JOIN customers on customers.id = transactions.customer_id\n" +
                    "JOIN course on course.id = transactions.course_id\n" +
                    "JOIN users u1 on u1.id = transactions.staff_id\n" +
                    "JOIN users u2 on u2.id = transactions.created_by order by transactions.id desc limit 1");
            rs = pst.executeQuery();
            if (rs.next()) {
                transaction.setId(rs.getString("id"));
                transaction.setCustomer(rs.getString("customer"));
                transaction.setCourse(rs.getString("course"));
                transaction.setStaff(rs.getString("staff_sup"));
                transaction.setPay(rs.getString("pay"));
                transaction.setNote(rs.getString("note"));
                transaction.setBooking(LocalDate.parse(rs.getString("booking")));
                transaction.setCreateBy(rs.getString("create_by"));
            }
            return transaction;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }

    // get Transaction by id
    public static Transaction getById(int id) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Transaction transaction = new Transaction();
        try {
            pst = connection.prepareStatement("SELECT transactions.id, customers.fullname as customer , course.name as course , u1.fullname as staff_sup , transactions.pay , transactions.note , transactions.booking, transactions.created_by as staff_id , u2.fullname as create_by\n" +
                    "FROM `transactions` \n" +
                    "JOIN customers on customers.id = transactions.customer_id\n" +
                    "JOIN course on course.id = transactions.course_id\n" +
                    "JOIN users u1 on u1.id = transactions.staff_id\n" +
                    "JOIN users u2 on u2.id = transactions.created_by WHERE transactions.id =" + id);
            rs = pst.executeQuery();
            if (rs.next()) {
                transaction.setId(rs.getString("id"));
                transaction.setCustomer(rs.getString("customer"));
                transaction.setCourse(rs.getString("course"));
                transaction.setStaff(rs.getString("staff_sup"));
                transaction.setPay(rs.getString("pay"));
                transaction.setNote(rs.getString("note"));
                transaction.setBooking(LocalDate.parse(rs.getString("booking")));
                transaction.setCreateBy(rs.getString("create_by"));
                transaction.setStaff_id(rs.getInt("staff_id"));
            }
            return transaction;

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
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "INSERT into " +
                            "transactions(customer_id , course_id , staff_id , pay , note , booking , created_by ) " +
                            "values (?,?,?,?,?,?,?)");
            statement.setString(1, customer_id);
            statement.setString(2, course_id);
            statement.setString(3, staff_id);
            statement.setString(4, pay);
            statement.setString(5, note);
            statement.setDate(6, Date.valueOf(booking));
            statement.setString(7, create_by);

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
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE transactions set customer_id = ? , course_id = ? , staff_id = ? , pay = ? , note = ? , booking = ? where id = ?");

            statement.setString(1, customer_id);
            statement.setString(2, course_id);
            statement.setString(3, staff_id);
            statement.setString(4, pay);
            statement.setString(5, note);
            statement.setString(6, String.valueOf(booking));
            statement.setString(7, id);

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            db.closeAll(connection, statement, null);
        }
    }

    public static ObservableList<Transaction> search(String text) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("SELECT transactions.id, customers.fullname as customer , course.name as course , u1.fullname as staff_sup , transactions.pay , transactions.note , transactions.booking , u2.fullname as create_by FROM `transactions` JOIN customers on customers.id = transactions.customer_id JOIN course on course.id = transactions.course_id JOIN users u1 on u1.id = transactions.staff_id JOIN users u2 on u2.id = transactions.created_by where customers.fullname like '%" + text + "%' or course.name like '%\" + text + \"%' or transactions.note like '%\" + text + \"%' or u2.fullname like '%\" + text + \"%'");
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

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String newId) {
        id.set(newId);
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public String getCustomer() {
        return customer.get();
    }

    public void setCustomer(String newCustomer) {
        customer.set(newCustomer);
    }

    public SimpleStringProperty courseProperty() {
        return course;
    }

    public String getCourse() {
        return course.get();
    }

    public void setCourse(String newCourse) {
        course.set(newCourse);
    }

    public SimpleStringProperty staffProperty() {
        return staff;
    }

    public String getStaff() {
        return staff.get();
    }

    public void setStaff(String newStaff) {
        staff.set(newStaff);
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public String getNote() {
        return note.get();
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

    public String getPay() {
        return pay.get();
    }

    // ORDER BY id DESC LIMIT 1;

    public void setPay(String newPay) {
        pay.set(newPay);
    }

    public SimpleObjectProperty<LocalDate> bookingProperty() {
        return booking;
    }

    public LocalDate getBooking() {
        return booking.get();
    }

    public void setBooking(LocalDate newBooking) {
        booking.set(newBooking);
    }
}

