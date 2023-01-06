package com.viespa.models;

import com.viespa.utils.DButil;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyChart {

    private String month;
    private Long revenue;
    private double avgRevenue;

    public String getMonth() {
        return month;
    }

    public Long getRevenue() {
        return revenue;
    }

    public Double getAvgRevenue() {
        return avgRevenue;
    }

    public MyChart(String month, String revenue, String avgRevenue) {
        this.month = month;
        this.revenue = Long.parseLong(revenue);
        this.avgRevenue = Double.parseDouble(avgRevenue);
    }

    // SELECT month(booking) AS month_name, SUM(pay) AS sum_of_month, AVG(pay) AS AVG_of_month FROM transactions GROUP BY month(booking) ORDER BY month_name DESC LIMIT 12;

    public static List<MyChart> getMonthlyRevenue (){
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<MyChart> myCharts = new ArrayList<>();
        try {
            pst = connection.prepareStatement("SELECT month(booking) AS month_name, SUM(pay) AS sum_of_month, AVG(pay) AS AVG_of_month FROM transactions GROUP BY month(booking) ORDER BY month_name DESC LIMIT 12;");
            rs = pst.executeQuery();
            while (rs.next()) {
                myCharts.add(new MyChart(rs.getString("month_name"), rs.getString("sum_of_month"), rs.getString("AVG_of_month")) );
            }
            return myCharts;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }
    public static List<MyChart> customerPerStaff (){
        DButil db = new DButil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<MyChart> myCharts = new ArrayList<>();
        try {
            pst = connection.prepareStatement("SELECT users.fullname as name, COUNT(transactions.id) as count FROM transactions join users on transactions.staff_id = users.id GROUP BY users.id;");
            rs = pst.executeQuery();
            while (rs.next()) {
                myCharts.add(new MyChart(rs.getString("name"), rs.getString("count"), "0" ));
            }
            return myCharts;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }
}
