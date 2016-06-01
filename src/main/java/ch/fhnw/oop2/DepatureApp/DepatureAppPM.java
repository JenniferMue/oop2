package ch.fhnw.oop2.DepatureApp;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jennifer Müller on 24.04.2016.
 */
public class DepatureAppPM {
    private static final String FILE_NAME = "olten.csv";
    private static final String SEMIKOLON = ";";
    private DepatureAppPM pm;
    private final StringProperty applicationTitle = new SimpleStringProperty("Departure");
    static ObservableList<Departure> departures = FXCollections.observableArrayList();

    private ObjectProperty<Departure> selectedDeparture = new SimpleObjectProperty<>();

    private TableView<Departure> tableView = new TableView<>();

    public DepatureAppPM() {
        departures.addAll(readFromFile());
        setSelectedDeparture(departures.get(0));
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME, true))) {
            writer.write("id ;Uhrzeit;Zugnummer;in Richtung;Über;Gleis");
            writer.newLine();
            departures.stream().forEach(departures -> {
                try {
                    writer.write(departures.infoAsLine());
                    writer.newLine();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("saive failed");
        }
    }



    private List<Departure> readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME)) {
            return stream.skip(1)                              // erstew Zeile ist die Headerzeile; ueberspringen
                    .map(s -> new Departure(s.split(SEMIKOLON))) // aus jeder Zeile ein Objekt machen
                    .collect(Collectors.toList());        // alles aufsammeln
        }
    }

    private Stream<String> getStreamOfLines(String fileName) {
        try {
            return Files.lines(getPath(fileName, true), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getPath(String fileName, boolean locatedInSameFolder) {
        try {
            if (!locatedInSameFolder) {
                fileName = "/" + fileName;
            }
            return Paths.get(getClass().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

    }

    // ADD-Methode

    public String[] list = {"0", " ", " ", " ", " ", " "};

    public void addNewDeparture(TableView tableView) {
        int scroll = 0;
        departures.add(new Departure(list)); // neues Element mit default-Werten von list übernehmen
        tableView.getSelectionModel().selectLast();// selektiert die neue Departure
        scroll = tableView.getSelectionModel().getSelectedIndex(); //index vom selektierten (neu hinzugefügten) Departure
        tableView.scrollTo(scroll); //scrollt ans Ende
    }

    //Delete-Methode
    public void removeDeparture(TableView tableView) {
        departures.remove(tableView.getSelectionModel().getSelectedIndex());
    }


    //Getter Setter

    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public ObservableList<Departure> getDepatures() {
        return departures;
    }

    public static String getFileName() {
        return FILE_NAME;
    }

    public static String getSEMIKOLON() {
        return SEMIKOLON;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public Departure getSelectedDeparture() {
        return selectedDeparture.get();
    }

    public ObjectProperty<Departure> selectedDepartureProperty() {
        return selectedDeparture;
    }

    public void setSelectedDeparture(Departure selectedDeparture) {
        this.selectedDeparture.set(selectedDeparture);
    }
}