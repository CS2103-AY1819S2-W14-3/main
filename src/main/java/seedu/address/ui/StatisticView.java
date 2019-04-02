package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 *  Initializes the Statistics Window
 */
public class StatisticView extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "StatisticView.fxml";

    @FXML
    private StackPane statisticView;

    // set constructor which one
    public StatisticView(Stage root, XYChart.Series statsData) {
        super(FXML, root);
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> statsChart = new BarChart<>(xAxis, yAxis);
        statsChart.getData().add(statsData);
        statsChart.setLegendVisible(false);
        statsChart.setTitle("Gameplay Summary");
        xAxis.setLabel("Your Statistics");
        yAxis.setLabel("");
        statisticView.getChildren().add(statsChart);

    }


    /**
     * Opens pop-up window for statistics.
     */
    public void show() {
        logger.fine("Displaying Statistics");
        getRoot().show();
    }
}
