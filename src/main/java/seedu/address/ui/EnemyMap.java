package seedu.address.ui;

import javafx.beans.value.ObservableBooleanValue;
import seedu.address.model.MapGrid;
import seedu.address.model.cell.Status;

/**
 * Panel containing the list of persons.
 */
public class EnemyMap extends Map {

    public EnemyMap(ObservableBooleanValue modelUpdateObservable, MapGrid mapGrid) {
        super(modelUpdateObservable, mapGrid);
    }

    @Override
    protected Status[][] getMapView(MapGrid mapGrid) {
        return mapGrid.getEnemyMapView();
    }
}
