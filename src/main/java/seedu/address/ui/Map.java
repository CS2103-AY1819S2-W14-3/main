package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.MapGrid;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Status;

/**
 * Panel containing the list of persons.
 */
public abstract class Map extends UiPart<Region> {
    private static final String FXML = "PlayerMap.fxml";
    private final Logger logger = LogsCenter.getLogger(Map.class);

    private int columnLabel = 1;
    private char rowLabel = 'A';
    private Cell[][] mapArray;

    @FXML
    private VBox grid;

    public Map(ObservableBooleanValue modelUpdateObservable, MapGrid mapGrid) {
        super(FXML);

        modelUpdateObservable.addListener(observable -> {
            int size = mapGrid.getMapSize();
            Status[][] mapView = getMapView(mapGrid);
            columnLabel = 1;
            rowLabel = 'A';

            grid.getChildren().clear();

            for (int i = 0; i < size + 1; i++) {
                HBox row = new HBox();
                for (int j = 0; j < size + 1; j++) {
                    StackPane sp;
                    if (i == 0 && j == 0) {
                        sp = getUiCell("", Color.WHITE);
                    } else if (i == 0) { // if first row
                        sp = getRowLabelCell();
                    } else if (j == 0) {
                        sp = getColumnLabelCell();
                    } else {
                        sp = getUiCell("", getColor(mapView[i - 1][j - 1]));
                    }

                    row.getChildren().add(sp);
                }
                grid.getChildren().add(row);
            }
        });
    }

    private StackPane getUiCell(String label, Color fillColour) {
        Rectangle cell = new Rectangle(30, 30);
        cell.setStroke(Color.BLACK);
        cell.setFill(fillColour);

        Text text = new Text(label);
        StackPane sp = new StackPane();
        sp.getChildren().addAll(cell, text);
        return sp;
    }

    private StackPane getColumnLabelCell() {
        StackPane sp = getUiCell(String.valueOf(rowLabel++), Color.WHITE);
        return sp;
    }

    private StackPane getRowLabelCell() {
        StackPane sp = getUiCell(String.valueOf(columnLabel++), Color.WHITE);
        return sp;
    }

    protected abstract Status[][] getMapView(MapGrid mapGrid);
    /**
     * Determine color of cell from the status of cell
     */
    Color getColor(Status status) {
        switch (status) {
        case HIDDEN:
            return Color.CADETBLUE;
        case SHIP:
            return Color.BLACK;
        case SHIPHIT:
            return Color.ORANGE;
        case EMPTY:
            return Color.LIGHTBLUE;
        case EMPTYHIT:
            return Color.DARKBLUE;
        case DESTROYED:
            return Color.RED;
        default:
            return Color.WHITE;
        }
    }
}
