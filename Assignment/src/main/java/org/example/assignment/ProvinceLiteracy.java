package org.example.assignment;

import javafx.scene.chart.XYChart;

public class ProvinceLiteracy {
    private String province;
    private double averageLiteracyRate;

    // Constructor
    public ProvinceLiteracy(String province, double averageLiteracyRate) {
        this.province = province;
        this.averageLiteracyRate = averageLiteracyRate;
    }

    // Getter and setter methods
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getAverageLiteracyRate() {
        return averageLiteracyRate;
    }

    public void setAverageLiteracyRate(double averageLiteracyRate) {
        this.averageLiteracyRate = averageLiteracyRate;
    }

    // Method to convert to XYChart.Data
    public XYChart.Data<String, Number> toChartData() {
        return new XYChart.Data<>(province, averageLiteracyRate);
    }
}


