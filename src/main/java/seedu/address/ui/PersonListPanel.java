package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cell.Cell;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Cell> personListView;

    public PersonListPanel(ObservableList<Cell> cellList, ObservableValue<Cell> selectedPerson,
                           Consumer<Cell> onSelectedPersonChange) {
        super(FXML);
        personListView.setItems(cellList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in cell list panel changed to : '" + newValue + "'");
            onSelectedPersonChange.accept(newValue);
        });
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected cell changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected cell,
            // otherwise we would have an infinite loop.
            if (Objects.equals(personListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                personListView.getSelectionModel().clearSelection();
            } else {
                int index = personListView.getItems().indexOf(newValue);
                personListView.scrollTo(index);
                personListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Cell} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Cell> {
        @Override
        protected void updateItem(Cell cell, boolean empty) {
            super.updateItem(cell, empty);

            if (empty || cell == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(cell, getIndex() + 1).getRoot());
            }
        }
    }

}
