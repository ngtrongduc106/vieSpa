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

public class Role {
    private final SimpleLongProperty id = new SimpleLongProperty();

    private final SimpleStringProperty role = new SimpleStringProperty();

    public static ObservableList<Role> getAllRole() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<Role> roles = FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("SELECT * FROM roles");
//            pst.setString(1, "1");
            rs = pst.executeQuery();
            while (rs.next()) {
                Role it = new Role();
                it.setId(Long.valueOf(rs.getString("id")));
                it.setRole(rs.getString("role_name"));
                roles.add(it);
            }

            return roles;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }

    public static String queryRoleId(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT id from roles where role_name = ?");
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

    public static String queryRoleName(String data) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT role_name from roles where id = ?");
            statement.setString(1, data);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role_name");
            } else {
                return null;
            }
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

    public void setId(Long newId) {
        id.set(newId);
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
}
