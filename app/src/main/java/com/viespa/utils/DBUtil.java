package com.viespa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbviespa";
    private static final String DB_USERNAME = "root" ;
    private static final String DB_PASSWORD = "" ;

    Connection conn ;

    public Connection connect(){
        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        }catch (Exception e){
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }

    public void closeAll(Connection connection , PreparedStatement statement , ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
