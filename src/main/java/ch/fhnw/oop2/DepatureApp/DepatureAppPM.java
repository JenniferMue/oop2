package ch.fhnw.oop2.DepatureApp;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EventListener;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jennifer Müller on 24.04.2016.
 */
public class DepatureAppPM {
    private static final String FILE_NAME = "olten.csv";
    private static final String SEMIKOLON = ";";
    private final StringProperty applicationTitle = new SimpleStringProperty("Departure");
    private ObservableList<Depature> depatures = FXCollections.observableArrayList();
    private  Depature selectedDeparture; // für Binding wichtig!!

    public DepatureAppPM() {
        depatures.addAll(readFromFile());
        selectedDeparture=depatures.get(0); // TODO diese Zeilen werden eingelesen, noch nicht fertig! Nur das Binding funktioniert
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME, true))) {
            writer.write("id ;Uhrzeit;Zugnummer;in Richtung;Über;Gleis");
            writer.newLine();
            depatures.stream().forEach(departures -> {
                try {
                    writer.write(departures.infoAsLine());
                    writer.newLine();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("save failed");
        }
    }


    private List<Depature> readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME)) {
            return stream.skip(1)                              // erste Zeile ist die Headerzeile; ueberspringen
                    .map(s -> new Depature(s.split(SEMIKOLON))) // aus jeder Zeile ein Objekt machen
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

    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public ObservableList<Depature> getDepatures() {
        return depatures;
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

    public Depature getSelectedDeparture() {
        return selectedDeparture;
    }

    public void setSelectedDeparture(Depature selectedDeparture) {
        this.selectedDeparture = selectedDeparture;
    }

}