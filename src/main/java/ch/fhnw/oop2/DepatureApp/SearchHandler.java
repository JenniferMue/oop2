package ch.fhnw.oop2.DepatureApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Created by Jennifer Müller on 07.06.2016.
 */

public class SearchHandler {
    private DepatureAppPM depatureAppPM = new DepatureAppPM();



    @FXML
    public TextField filterField;
    @FXML
    private TableView<Departure> departureTable;
    @FXML
    private TableColumn<Departure, String> abfahrtColumn;

    private ObservableList<Departure> masterData = FXCollections.observableArrayList();

    public SearchHandler(TableView<Departure> departureTable) {
        this.departureTable=departureTable;
        initialize();
            }

    @FXML
    private void initialize() {
        // 0. Initialize the columns.
        abfahrtColumn.setCellValueFactory(cellData -> cellData.getValue().inRichtungProperty());

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Departure> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(departure -> {
                // If filter text is empty, display all departures.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (departure.getInRichtung().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (departure.getInRichtung().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Departure> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(departureTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        departureTable.setItems(sortedData);
    }

}