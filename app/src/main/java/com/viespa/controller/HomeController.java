package com.viespa.controller;

import com.viespa.models.MyChart;
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
    private LineChart<Number, Number> lineChart;

    @FXML
    private CategoryAxis xAxisLC;

    @FXML
    private NumberAxis yAxisLC;

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
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        pieChart.setData(pieChartData);
        pieChart.setTitle("Imported Fruits");
    }

//    private void setLineChart() {
//        xAxisLC.setLabel("Number of Month");
//        yAxisLC.setLabel("Value");
//        lineChart.setTitle("Stock Monitoring, 2010");
//        //defining a series
//        XYChart.Series series = new XYChart.Series();
//        series.setName("My portfolio");
//        //populating the series with data
//        series.getData().add(new XYChart.Data(1, 23));
//        series.getData().add(new XYChart.Data(2, 14));
//        series.getData().add(new XYChart.Data(3, 15));
//        series.getData().add(new XYChart.Data(4, 24));
//        series.getData().add(new XYChart.Data(5, 34));
//        series.getData().add(new XYChart.Data(6, 36));
//        series.getData().add(new XYChart.Data(7, 22));
//        series.getData().add(new XYChart.Data(8, 45));
//        series.getData().add(new XYChart.Data(9, 43));
//        series.getData().add(new XYChart.Data(10, 17));
//        series.getData().add(new XYChart.Data(11, 29));
//        series.getData().add(new XYChart.Data(12, 25));
//
//        lineChart.getData().add(series);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBarChart();
        setPieChart();
//        setLineChart();
    }

}
