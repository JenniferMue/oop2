package ch.fhnw.oop2.DepatureApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Steven on 24.05.16.
 */
public class LanguageHandler {
    private DepatureAppUI ui;
    private ObservableList<Locale> ol;

    public LanguageHandler(DepatureAppUI ui) {
        this.ui = ui;
        this.ol = FXCollections.observableArrayList();
        ol.addAll(Locale.GERMAN, Locale.ENGLISH, Locale.ITALIAN, Locale.FRENCH);
    }

    public ObservableList<Locale> getList(){
        return ol;
    }

    public void changeLanguage (Locale abbrev) {

        try {
            String baseName =  "DepartureBundles";
            ResourceBundle bundle = ResourceBundle.getBundle(baseName, abbrev);
            ui.setAbfahrt(bundle.getString("Abfahrt"));
            ui.setGleis(bundle.getString("Gleis"));
            ui.setNach(bundle.getString("nach"));
            ui.setZugnummer(bundle.getString("Zugnummer"));
            ui.setZwischenhalte(bundle.getString("Zwischenhalte"));
            ui.setDeparture(bundle.getString("Departure"));
            ui.setTo(bundle.getString("To"));
            ui.setTrack(bundle.getString("Track"));

        } catch(MissingResourceException exception) {
            System.out.println(exception.getMessage());

        }
    }
}
