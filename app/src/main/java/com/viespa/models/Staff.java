package com.viespa.models;

import com.viespa.utils.DBUtil;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Staff {
    private final SimpleLongProperty id = new SimpleLongProperty();
    private final SimpleStringProperty account = new SimpleStringProperty();
    private final SimpleStringProperty fullname = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> dob = new SimpleObjectProperty<>();
    private final SimpleStringProperty phone = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleStringProperty role = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> joinDate = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final SimpleStringProperty status = new SimpleStringProperty();

    public String getRoleName() {
        return roleName.get();
    }

    public SimpleStringProperty roleNameProperty() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }

    private final SimpleStringProperty roleName = new SimpleStringProperty();

    public Staff() {
    }

    public static ObservableList<Staff> getAllStaffs() {
        {
            DBUtil db = new DBUtil();
            Connection connection = db.connect();
            PreparedStatement pst = null;
            ResultSet rs = null;
            ObservableList<Staff> staffs = FXCollections.observableArrayList();
            try {
                pst = connection.prepareStatement("SELECT users.id, users.account, users.fullname, users.dob, users.phone, users.email, users.address, roles.role_name , users.joindate, users.enddate, users.status \n" +
                        "FROM `users` JOIN roles ON roles.id = users.role\n" );
                rs = pst.executeQuery();
                while (rs.next()) {
                    Staff it = new Staff();
                    it.setId(Long.valueOf(rs.getString("id")));
                    it.setAccount(rs.getString("account"));
                    it.setFullName(rs.getString("fullname"));
                    it.setDob((rs.getObject("dob", LocalDate.class)));
                    it.setPhone(rs.getString("phone"));
                    it.setEmail(rs.getString("email"));
                    it.setAddress(rs.getString("address"));
                    it.setRole((rs.getString("role_name")));
                    it.setJoinDate((rs.getObject("joindate", LocalDate.class)));
                    it.setEndDate((rs.getObject("enddate", LocalDate.class)));
                    it.setStatus(rs.getString("status"));
                    staffs.add(it);
                }

                return staffs;

            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                db.closeAll(connection, pst, rs);
            }
        }
    }

    public static ObservableList<Staff> getActiveStaffs() {
        {
            DBUtil db = new DBUtil();
            Connection connection = db.connect();
            PreparedStatement pst = null;
            ResultSet rs = null;
            ObservableList<Staff> staffs = FXCollections.observableArrayList();
            try {
                pst = connection.prepareStatement("SELECT users.id, users.account, users.fullname, users.dob, users.phone, users.email, users.address, roles.role_name , users.joindate, users.enddate, users.status \n" +
                        "FROM `users` JOIN roles ON roles.id = users.role\n" +
                        "WHERE role != '1' AND isempty(enddate)");
                rs = pst.executeQuery();
                while (rs.next()) {
                    Staff it = new Staff();
                    it.setId(Long.valueOf(rs.getString("id")));
                    it.setAccount(rs.getString("account"));
                    it.setFullName(rs.getString("fullname"));
                    it.setDob((rs.getObject("dob", LocalDate.class)));
                    it.setPhone(rs.getString("phone"));
                    it.setEmail(rs.getString("email"));
                    it.setAddress(rs.getString("address"));
                    it.setRole((rs.getString("role_name")));
                    it.setJoinDate((rs.getObject("joindate", LocalDate.class)));
                    it.setEndDate((rs.getObject("enddate", LocalDate.class)));
                    it.setStatus(rs.getString("status"));
                    staffs.add(it);
                }

                return staffs;

            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                db.closeAll(connection, pst, rs);
            }
        }
    }
    public static void addStaff(String account,
                                String password,
                                String fullname,
                                String address,
                                String email,
                                String phone,
                                int role,
                                LocalDate dob,
                                LocalDate joinDate,
                                String status) throws SQLException {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "INSERT into " +
                            "users(account, password, fullname, dob, phone, email , address , role , joindate , status) " +
                            "values (?,?,?,?,?,?,?,?,?,?)");

            statement.setString(1, account);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setDate(4, Date.valueOf(dob));
            statement.setString(5, phone);
            statement.setString(6, email);
            statement.setString(7, address);
            statement.setInt(8, role);
            statement.setDate(9, Date.valueOf(joinDate));
            statement.setString(10, status);

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            db.closeAll(connection, statement, null);
        }
    }

    public static void updateStaff(
            int id,
            String account,
            String fullname,
            String address,
            String email,
            String phone,
            int role,
            LocalDate dob,
            LocalDate joinDate,
            @Nullable LocalDate endDate,
            String status) throws SQLException {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                    "UPDATE users SET account = ? , fullname = ? , dob = ? , phone = ? , email = ? , address = ? , role = ? , joindate = ? , enddate = ?, status = ? where id = ?");


            statement.setString(1, account);
            statement.setString(2, fullname);
            statement.setDate(3, Date.valueOf(dob));
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.setString(6, address);
            statement.setInt(7, role);
            statement.setDate(8, Date.valueOf(joinDate));
            statement.setDate(9, endDate != null ? Date.valueOf(endDate) : null);
            statement.setString(10,status);
            statement.setString(11, String.valueOf(id));

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            db.closeAll(connection, statement, null);
        }
    }

    public static boolean checkDuplicate(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("select * from users where account = ? ");
            statement.setString(1, data);
            rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.closeAll(connection, statement, rs);
        }
    }

    public static String queryId(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT id from users where fullname = ? AND role != ?");
            statement.setString(1, data);
            statement.setString(2, "1");
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

    public static Staff getById(String id) {
        {
            DBUtil db = new DBUtil();
            Connection connection = db.connect();
            PreparedStatement pst = null;
            ResultSet rs = null;
            try {
                pst = connection.prepareStatement("select users.id, users.account, users.fullname,users.dob , users.phone, users.email, users.address, users.joindate, users.enddate, users.status, users.role, r.role_name from users join roles r on r.id = users.role where users.id = ?");
                pst.setString(1, id);
                rs = pst.executeQuery();
                Staff it = new Staff();
                if (rs.next()) {
                    it.setId(Long.valueOf(rs.getString("id")));
                    it.setAccount(rs.getString("account"));
                    it.setFullName(rs.getString("fullname"));
                    it.setDob((rs.getObject("dob", LocalDate.class)));
                    it.setPhone(rs.getString("phone"));
                    it.setEmail(rs.getString("email"));
                    it.setAddress(rs.getString("address"));
                    it.setRole((rs.getString("role")));
                    it.setRoleName(rs.getString("role_name"));
                    it.setJoinDate((rs.getObject("joindate", LocalDate.class)));
                    it.setEndDate((rs.getObject("enddate", LocalDate.class)));
                    it.setStatus((rs.getString("status")));
                }

                return it;

            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                db.closeAll(connection, pst, rs);
            }
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

    public SimpleStringProperty accountProperty() {
        return account;
    }

    public String getAccount() {
        return account.get();
    }

    public void setAccount(String newAccount) {
        account.set(newAccount);
    }

    public SimpleStringProperty fullNameProperty() {
        return fullname;
    }

    public String getFullname() {
        return fullname.get();
    }

    public void setFullName(String newFullName) {
        fullname.set(newFullName);
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

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String newPhone) {
        phone.set(newPhone);
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

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String newAddress) {
        address.set(newAddress);
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String newRole) {
        role.set(newRole);
    }

    public SimpleObjectProperty<LocalDate> joinDateProperty() {
        return joinDate;
    }

    public LocalDate getJoinDate() {
        return joinDate.get();
    }

    public void setJoinDate(LocalDate newJoinDate) {
        joinDate.set(newJoinDate);
    }

    public SimpleObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    //get by ID

    public void setEndDate(LocalDate newEndDate) {
        endDate.set(newEndDate);
    }

    public SimpleStringProperty statusProperty(){return status;}

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String newStatus){
        status.set(newStatus);
    }
}
