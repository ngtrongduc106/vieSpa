package com.viespa.controller;

import com.viespa.models.MyChart;
import com.viespa.models.MyPie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
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

    void setBarChart(){
        chart1.setTitle("Revenue");
        xAxis.setLabel("Name of Month");
        yAxis.setLabel("USD");

        List<MyChart> barCharts = MyChart.getMonthlyRevenue();
        XYChart.Series monthly = new XYChart.Series();
        monthly.setName("Monthly revenue");

        barCharts.forEach(it -> {
            monthly.getData().add(new XYChart.Data(it.getMonth(),it.getRevenue()));
        });
        XYChart.Series avgMonthly = new XYChart.Series();
        avgMonthly.setName("Monthly average");
        barCharts.forEach(it-> avgMonthly.getData().add(new XYChart.Data(it.getMonth(),it.getAvgRevenue())));

        chart1.getData().addAll(monthly, avgMonthly);
    }

    void setPieChart(){
        // Pie Chart
        pieChart.setData(MyPie.countCourse());
        pieChart.setTitle("Imported Fruits");
    }

    void setBarChart2(){
        barChart2.setTitle("Customer per staff");
        xChart2.setLabel("Name of the staff");
        yChart2.setLabel("Count of customer");

        List<MyChart> barCharts = MyChart.customerPerStaff();
        XYChart.Series item = new XYChart.Series();
        item.setName("Staff");

        barCharts.forEach(it -> {
            item.getData().add(new XYChart.Data(it.getMonth(),it.getRevenue()));
        });
        barChart2.getData().addAll(item);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBarChart();
        setPieChart();
        setBarChart2();
    }

}
