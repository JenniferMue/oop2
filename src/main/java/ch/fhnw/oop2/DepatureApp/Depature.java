package ch.fhnw.oop2.DepatureApp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Jennifer Müller on 02.05.2016.
 */

public class Depature {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty uhrzeit = new SimpleStringProperty(); // Klasse suchen (Date zB)
    private final StringProperty zugnummer = new SimpleStringProperty();
    private final StringProperty inRichtung = new SimpleStringProperty();
    private final StringProperty ueber = new SimpleStringProperty();
    private final StringProperty gleis = new SimpleStringProperty();



    public Depature(String[] line) {
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

        Depature depature = (Depature) o;

        return getId().equals(depature.getId());
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
}