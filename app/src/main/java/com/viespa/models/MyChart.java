package com.viespa.models;

import com.viespa.utils.DBUtil;
import com.viespa.utils.DateUtil;

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

    public MyChart(String month, String revenue, String avgRevenue) {
        this.month = month;
        this.revenue = Long.parseLong(revenue);
        this.avgRevenue = Double.parseDouble(avgRevenue);
    }

    public static List<MyChart> getMonthlyRevenue() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<MyChart> myCharts = new ArrayList<>();
        try {
            pst = connection.prepareStatement("SELECT month(booking) AS month_name, SUM(pay) AS sum_of_month, AVG(pay) AS AVG_of_month FROM transactions GROUP BY month(booking) ORDER BY month_name DESC LIMIT 12;");
            rs = pst.executeQuery();
            while (rs.next()) {
                myCharts.add(new MyChart(rs.getString("month_name"), rs.getString("sum_of_month"), rs.getString("AVG_of_month")));
            }
            return myCharts;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }

    public static List<MyChart> customerPerStaff() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<MyChart> myCharts = new ArrayList<>();
        try {
            pst = connection.prepareStatement("SELECT users.fullname as name, COUNT(transactions.id) as count FROM transactions join users on transactions.staff_id = users.id GROUP BY users.id;");
            rs = pst.executeQuery();
            while (rs.next()) {
                myCharts.add(new MyChart(rs.getString("name"), rs.getString("count"), "0"));
            }
            return myCharts;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }

    // SELECT weekday(booking) AS week_name, count(transactions.id) FROM transactions where (month(now()) = month(booking) AND year(now()) = year(booking)) GROUP BY weekday(booking) ORDER BY week_name;
    public static List<MyChart> monthReport(int i) {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<MyChart> myCharts = new ArrayList<>();
        try {
            pst = i == 1 ?
                    connection.prepareStatement("SELECT weekday(booking) AS week_name, count(transactions.id) as count FROM transactions where if(month(now())>1,(month(booking) = (month(now()) -1) AND year(now()) = year(booking)),(month(booking) = 12 AND year(booking) = year(now())-1))GROUP BY weekday(booking) ORDER BY week_name;") :
                    connection.prepareStatement("SELECT weekday(booking) AS week_name, count(transactions.id) as count FROM transactions where (month(booking) = month(now()) AND year(now()) = year(booking)) GROUP BY weekday(booking) ORDER BY week_name;");
            rs = pst.executeQuery();
            while (rs.next()) {
                myCharts.add(new MyChart(DateUtil.toWeekDay(rs.getInt("week_name")), rs.getString("count"), "0"));
            }
            return myCharts;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }
    }

    // SELECT month(booking) AS month_name, SUM(pay) AS sum_of_month, AVG(pay) AS AVG_of_month FROM transactions GROUP BY month(booking) ORDER BY month_name DESC LIMIT 12;

    public String getMonth() {
        return month;
    }

    public Long getRevenue() {
        return revenue;
    }

    public Double getAvgRevenue() {
        return avgRevenue;
    }

}
