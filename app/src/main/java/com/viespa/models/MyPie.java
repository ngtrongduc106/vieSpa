package com.viespa.models;

import com.viespa.utils.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyPie {
    private String name;
    private int count;

    public MyPie(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public static ObservableList<PieChart.Data> countCourse() {
        DBUtil db = new DBUtil();
        Connection connection = db.connect();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<PieChart.Data> myPies = FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("SELECT course.name as name, COUNT(transactions.id) as count FROM transactions join course on course_id = course.id GROUP BY course_id;");
            rs = pst.executeQuery();
            while (rs.next()) {
                myPies.add(new PieChart.Data(rs.getString("name"), rs.getInt("count")));
            }
            return myPies;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.closeAll(connection, pst, rs);
        }

    }
}
