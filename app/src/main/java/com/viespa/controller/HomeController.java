package com.viespa.controller;

import com.viespa.models.MyChart;
import com.viespa.models.MyPie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private BarChart<String, Number> chart1;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<Number, Number> barChart2;

    @FXML
    private CategoryAxis xChart2;

    @FXML
    private NumberAxis yChart2;

    @FXML
    private BarChart<Number, Number> barChart3;

    void setBarChart() {
        chart1.setTitle("Revenue");
        xAxis.setLabel("Name of Month");
        yAxis.setLabel("USD");

        List<MyChart> barCharts = MyChart.getMonthlyRevenue();
        XYChart.Series monthly = new XYChart.Series();
        monthly.setName("Monthly revenue");

        barCharts.forEach(it -> {
            monthly.getData().add(new XYChart.Data(it.getMonth(), it.getRevenue()));
        });
//        XYChart.Series avgMonthly = new XYChart.Series();
//        avgMonthly.setName("Monthly average");
//        barCharts.forEach(it-> avgMonthly.getData().add(new XYChart.Data(it.getMonth(),it.getAvgRevenue())));

        chart1.getData().addAll(monthly);
    }

    void setPieChart() {
        // Pie Chart
        pieChart.setData(MyPie.countCourse());
        pieChart.setTitle("Total sale per service");
    }

    void setBarChart2() {
        barChart2.setTitle("Customer per staff");
        xChart2.setLabel("Name of the staff");
        yChart2.setLabel("Count of customer");

        List<MyChart> barCharts = MyChart.customerPerStaff();
        XYChart.Series item = new XYChart.Series();
        item.setName("Staff");

        barCharts.forEach(it -> {
            item.getData().add(new XYChart.Data(it.getMonth(), it.getRevenue()));
        });
        barChart2.getData().addAll(item);
    }

    void setBarChart3() {
        barChart3.setTitle("Sale this month compare to last month");
        List<MyChart> barCharts = MyChart.monthReport(0);
        List<MyChart> lastMonth = MyChart.monthReport(1);


        XYChart.Series item = new XYChart.Series();
        item.setName("This month");
        barCharts.forEach(it -> {
            item.getData().add(new XYChart.Data(it.getMonth(), it.getRevenue()));
        });

        XYChart.Series item2 = new XYChart.Series();
        item2.setName("Last month");
        lastMonth.forEach(it -> {
            item2.getData().add(new XYChart.Data(it.getMonth(), it.getRevenue()));
        });

        barChart3.getData().addAll(item2, item);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBarChart();
        setPieChart();
        setBarChart2();
        setBarChart3();
    }

}
