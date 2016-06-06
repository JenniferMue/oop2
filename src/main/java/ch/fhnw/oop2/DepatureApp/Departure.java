
package ch.fhnw.oop2.DepatureApp;

import ch.fhnw.oop2.led.LedBuilder;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

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
 * Creeated by Jennifer Müller on 02.05.2016.
 */

public class Departure {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty uhrzeit = new SimpleStringProperty(); // Klasse suchen (Date zB)
    private final StringProperty zugnummer = new SimpleStringProperty();
    private final StringProperty inRichtung = new SimpleStringProperty();
    private final StringProperty ueber = new SimpleStringProperty();
    private final StringProperty gleis = new SimpleStringProperty();
    private final ObjectProperty<Node> led = new SimpleObjectProperty<>(LedBuilder.create().on(true).ledColor(Color.GREEN).build());





    public Departure(String[] line) {
        setId(Integer.parseInt(line[0]));
        setUhrzeit(line[1]);
        setZugnummer(line[2]);
        setInRichtung(line[3]);
        setUeber(line[4]);
        setGleis(line[5]);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Departure departure = (Departure) o;

        return getId().equals(departure.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    public String infoAsLine() {
        return String.join(";",
                Integer.toString(getId()),
                getUhrzeit(),
                getZugnummer(),
                getInRichtung(),
                getUeber(),
                getGleis());
    }

    @Override
    public String toString() {
        return infoAsLine();
    }


    public StringProperty gleisNummerProberty() {
        return gleis;
    }


    // Getter & Setter

    public Node getLed() {
        return led.get();
    }

    public ObjectProperty<Node> ledProperty() {
        return led;
    }

    public void setLed(Node led) {
        this.led.set(led);
    }
    public Integer getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(Integer id) {
        this.id.set((id));
    }

    public String getUhrzeit() {
        return uhrzeit.get();
    }

    public StringProperty uhrzeitProperty() {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit.set(uhrzeit);
    }

    public String getZugnummer() {
        return zugnummer.get();
    }

    public StringProperty zugnummerProperty() {
        return zugnummer;
    }

    public void setZugnummer(String zugnummer) {
        this.zugnummer.set(zugnummer);
    }

    public String getInRichtung() {
        return inRichtung.get();
    }

    public StringProperty inRichtungProperty() {
        return inRichtung;
    }

    public void setInRichtung(String inRichtung) {
        this.inRichtung.set(inRichtung);
    }

    public String getUeber() {
        return ueber.get();
    }

    public StringProperty ueberProperty() {
        return ueber;
    }

    public void setUeber(String ueber) {
        this.ueber.set(ueber);
    }

    public String getGleis() {
        return gleis.get();
    }

    public StringProperty gleisProperty() {
        return gleis;
    }

    public void setGleis(String gleis) {
        this.gleis.set(gleis);
    }

    /**
     * Created by Jennifer Müller on 24.04.2016.
     */
    public static class DepatureAppPM {
        private static final String FILE_NAME = "olten.csv";
        private static final String SEMIKOLON = ";";
        private final StringProperty applicationTitle = new SimpleStringProperty("Departure");
        private ObservableList<Departure> departures = FXCollections.observableArrayList();
        private Departure selectedDeparture; // für Binding wichtig!!

        public DepatureAppPM() {
            departures.addAll(readFromFile());
            selectedDeparture = departures.get(0);
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
                throw new IllegalStateException("save failed");
            }
        }

        /*public void undo()
        {
            if (depatures.equals("add"))
            {
                try
                {
                    model.re(tier);
                }
                catch (Exception e)
                {
                }
            }
            else if (aktion.equals("remove"))
            {
                model.addTier(position, tier);
            }
            else
                ;

        }*/


        private List<Departure> readFromFile() {
            try (Stream<String> stream = getStreamOfLines(FILE_NAME)) {
                return stream.skip(1)                              // erste Zeile ist die Headerzeile; ueberspringen
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

        //Default-Einstellung für ein neues Departure
        public String[] list = {"0", " ", " ", " ", " ", " "};

        // Add Methode zum Hinzufügen von einem neuen Departure
        public void addNewDeparture() {
            departures.add(new Departure(list));
        }

        public String getApplicationTitle() {
            return applicationTitle.get();
        }

        public StringProperty applicationTitleProperty() {
            return applicationTitle;
        }

        public ObservableList<Departure> getDepartures() {
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
            return selectedDeparture;
        }

        public void setSelectedDeparture(Departure selectedDeparture) {
            System.out.println(selectedDeparture);
            this.selectedDeparture = selectedDeparture;
        }

    }
}