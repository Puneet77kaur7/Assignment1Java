package org.example.assignment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {

    private DatabaseManager databaseManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create DatabaseManager instance
            databaseManager = new DatabaseManager("jdbc:mysql://localhost:3306/BarGraph", "root", "1234");

            primaryStage.setTitle("Average Literacy Rate in Canadian Provinces");

            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));




            // Fetch data from database
            Connection connection = databaseManager.getConnection(); // Get connection from DatabaseManager
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery("SELECT province, AVG(literacy_rate) AS avg_literacy_rate FROM province_literacy GROUP BY province");

            // Create BarChart
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            xAxis.setLabel("Provinces");
            yAxis.setLabel("Avg Literacy Rate");


            // Populate chart from result set
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            while (result.next()) {
                String province = result.getString("province");
                double avgLiteracyRate = result.getDouble("avg_literacy_rate");
                series.getData().add(new XYChart.Data<>(province, avgLiteracyRate));
            }
            barChart.getData().add(series);

            // Create TableView
            TableView<ProvinceLiteracy> tableView = new TableView<>();
            // Create table columns
            TableColumn<ProvinceLiteracy, String> provinceColumn = new TableColumn<>("Province");
            provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));

            TableColumn<ProvinceLiteracy, Double> literacyRateColumn = new TableColumn<>("Average Literacy Rate");
            literacyRateColumn.setCellValueFactory(new PropertyValueFactory<>("averageLiteracyRate"));

            tableView.getColumns().addAll(provinceColumn, literacyRateColumn);

            // Populate TableView from result set
            result.beforeFirst(); // Move result set cursor back to the beginning
            while (result.next()) {
                tableView.getItems().add(new ProvinceLiteracy(result.getString("province"), result.getDouble("avg_literacy_rate")));
            }
            // Create a button to switch to TableView scene
            VBox chartBox = new VBox(barChart);
            Scene chartScene = new Scene(chartBox, 800, 500);

            Button backButton = new Button("Back to Chart");
            backButton.setOnAction(e -> primaryStage.setScene(chartScene));
            backButton.getStyleClass().add("button-Style");

            // Create a button to switch to TableView scene
            VBox tableBox = new VBox(tableView);
            Scene tableScene = new Scene(tableBox, 800, 600);

            Button switchToTableButton = new Button("Switch to Table");
            switchToTableButton.setOnAction(e -> primaryStage.setScene(tableScene));
            switchToTableButton.getStyleClass().add("button-Style");

            // Add the button to the chart scene
            chartBox.getChildren().add(switchToTableButton);
            tableBox.getChildren().add(backButton);

            // Load CSS file
            String cssResource = getClass().getResource("/style.css").toExternalForm();
            chartScene.getStylesheets().add(cssResource);
            tableScene.getStylesheets().add(cssResource);

            // Set main scene to BarChart scene
            primaryStage.setScene(chartScene);
            primaryStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        // Close database connection when application stops
        if (databaseManager != null) {
            databaseManager.closeConnection();
        }
        super.stop();
    }
}
