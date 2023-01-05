package com.viespa.controller;

import com.viespa.App;
import com.viespa.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private BarChart<String, Number> chart1;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    void chart1(){
        chart1.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data("austria", 25601.34));
        series1.getData().add(new XYChart.Data("brazil", 20148.82));
        series1.getData().add(new XYChart.Data("france", 10000));
        series1.getData().add(new XYChart.Data("italy", 35407.15));
        series1.getData().add(new XYChart.Data("usa", 12000));

        chart1.getData().addAll(series1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chart1();
    }

}
