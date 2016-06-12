package ch.fhnw.oop2.DepatureApp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Jennifer Müller on 24.04.2016.
 */

public class DepartureAppUI extends BorderPane {


    /*------------------------
        Attribute - Inhalt
     -------------------------*/
    private final DepatureAppPM pm;

    // Attribute Buttons etc. setzen für Top Bereich
    private HBox top;
    private HBox center;
    private HBox spacePane1;
    private Button speichern;
    private Button neu;
    private Button löschen;
    private Button undo;
    private Button redo;
    private Button zug;
    private Button anzeigeTafel;
    private Button pause;
    private Button start;
    private ComboBox<Locale> language;
    private LanguageHandler languagehandler;
    private TextField suche;

    /*------------------------
        Attribute - Layout
     -------------------------*/

    // Center. Im Center ist ein Splitpane implementiert
    private SplitPane splitPane;

    // im Splitpane wird eine linke und eine rechte Seite eingefügt
    // die linke Seite ist eine Tableview
    public TableView<Departure> leftSide;
    // die rechte Seite besteht aus einem Splitpane
    private GridPane rightSide;

    /*------------------------
        Attribute - Layout - linke Seite
     -------------------------*/
    private TableColumn<Departure, String> departure;
    private TableColumn<Departure, String> to;
    private TableColumn<Departure, String> track;
    private TableColumn<Departure, Node> led;

    /*------------------------
     Attribute - Layout - rechte Seite
     -------------------------*/

    private Label abfahrt;
    private Label nach;
    private Label zugnummer;
    private Label gleis;
    private Label zwischehalte;
    private TextField tfAbfahrt;
    private TextField tfNach;
    private TextField tfZugnummer;
    private TextField tfGleis;
    private TextArea taZwischenhalte;


     /*------------------------
        Setter Methoden für Labels
     -------------------------*/

    //setter Methoden für Labels
    public void setAbfahrt(String text) {
        abfahrt.textProperty().setValue(text);
    }

    public void setLed(String text) {
        led.textProperty().setValue(text);
    }

    public void setGleis(String text) {
        gleis.textProperty().setValue(text);
    }

    public void setZugnummer(String text) {
        zugnummer.textProperty().setValue(text);
    }

    public void setZwischenhalte(String text) {
        zwischehalte.textProperty().setValue(text);
    }

    public void setNach(String text) {
        nach.textProperty().setValue(text);
    }

    public void setDeparture(String text) {
        departure.textProperty().setValue(text);
    }

    public void setTo(String text) {
        to.textProperty().setValue(text);
    }

    public void setTrack(String text) {
        track.textProperty().setValue(text);
    }

    /*------------------------
          Konstruktor
     -------------------------*/
    public DepartureAppUI(DepatureAppPM pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        addEventHandlers();
        addValueChangedListeners();
        addBindings();
        addLanguageChanger();

    }

    /*------------------------
      Methoden für Konstruktor
   -------------------------*/
    private void addValueChangedListeners() {
        leftSide.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pm.setSelectedDeparture(newValue);
        });

        pm.selectedDepartureProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                tfAbfahrt.textProperty().unbindBidirectional(oldValue.uhrzeitProperty());
                tfGleis.textProperty().unbindBidirectional(oldValue.gleisProperty());
                tfZugnummer.textProperty().unbindBidirectional(oldValue.zugnummerProperty());
                taZwischenhalte.textProperty().unbindBidirectional(oldValue.ueberProperty());
                tfNach.textProperty().unbindBidirectional(oldValue.inRichtungProperty());
            }
            if (newValue != null) {
                tfAbfahrt.textProperty().bindBidirectional(newValue.uhrzeitProperty());
                tfGleis.textProperty().bindBidirectional(newValue.gleisProperty());
                tfZugnummer.textProperty().bindBidirectional(newValue.zugnummerProperty());
                taZwischenhalte.textProperty().bindBidirectional(newValue.ueberProperty());
                tfNach.textProperty().bindBidirectional(newValue.inRichtungProperty());
            }
        });
    }

    private void initializeControls() {
        /*------------------------
          Layout - Linker Bereich
         -------------------------*/
        departure = new TableColumn();
        departure.setCellValueFactory(param -> param.getValue().uhrzeitProperty());
        to = new TableColumn();
        to.setCellValueFactory(param -> param.getValue().inRichtungProperty());
        track = new TableColumn();
        track.setCellValueFactory(param -> param.getValue().gleisNummerProberty());
        led = new TableColumn();
        led.setMaxWidth(750);
        led.setCellValueFactory(param -> param.getValue().ledProperty());

        /*------------------------
          Layout - Rechter Bereich
         -------------------------*/
        abfahrt = new Label();
        nach = new Label();
        zugnummer = new Label();
        gleis = new Label();
        zwischehalte = new Label();
        tfAbfahrt = new TextField();
        tfNach = new TextField();
        tfZugnummer = new TextField();
        tfGleis = new TextField();
        taZwischenhalte = new TextArea();

        /*------------------------
          Layout - Top-Bereich
         -------------------------*/
        FontAwesomeIconView saveIcon = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
        speichern = new Button("", saveIcon);
        saveIcon.setId("Icon");
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
        löschen = new Button("", deleteIcon);
        deleteIcon.setId("Icon");
        FontAwesomeIconView newIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        neu = new Button("", newIcon);
        newIcon.setId("Icon");
        FontAwesomeIconView undoIcon = new FontAwesomeIconView(FontAwesomeIcon.REPLY);
        undo = new Button("", undoIcon);
        undoIcon.setId("Icon");
        FontAwesomeIconView redoIcon = new FontAwesomeIconView(FontAwesomeIcon.SHARE);
        redo = new Button("", redoIcon);
        redoIcon.setId("Icon");
        FontAwesomeIconView zugIcon = new FontAwesomeIconView(FontAwesomeIcon.TRAIN);
        zug = new Button("", zugIcon);
        zugIcon.setId("Icon");
        FontAwesomeIconView anzeigeIcon = new FontAwesomeIconView(FontAwesomeIcon.UPLOAD);
        anzeigeTafel = new Button("", anzeigeIcon);
        anzeigeIcon.setId("Icon");
        FontAwesomeIconView pauseIcon = new FontAwesomeIconView(FontAwesomeIcon.PAUSE);
        pause = new Button("", pauseIcon);
        pauseIcon.setId("Icon");
        FontAwesomeIconView startIcon = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        start = new Button("", startIcon);
        startIcon.setId("Icon");


        /*------------------------
          Layout - LanguageHandler
         -------------------------*/
        languagehandler = new LanguageHandler(this);
        language = new ComboBox<>(languagehandler.getList());
        language.setMinHeight(48);
        language.setId("lh");
        suche = new TextField("Suche");
        suche.setMinHeight(48);
        suche.setMinWidth(280);
        top = new HBox();

        /*------------------------
          Layout - Center
         -------------------------*/
        splitPane = new SplitPane();
        splitPane.setDividerPosition(0, 0.3);
        leftSide = new TableView(pm.getDepatures());
        leftSide.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        leftSide.setMinWidth(200);
        rightSide = new GridPane();

        /*------------------------
          Layout - linker Bereich
         -------------------------*/
        departure = new TableColumn("Abfahrt");
        departure.setCellValueFactory(param -> param.getValue().uhrzeitProperty());
        to = new TableColumn("nach");
        to.setCellValueFactory(param -> param.getValue().inRichtungProperty());
        track = new TableColumn("Gleis");
        track.setCellValueFactory(param -> param.getValue().gleisNummerProberty());

        /*------------------------
          Layout - rechter Bereich
         -------------------------*/
        abfahrt = new Label("Abfahrt");
        nach = new Label("nach");
        zugnummer = new Label("Zugnummer");
        gleis = new Label("Gleis");
        zwischehalte = new Label("Zwischenhalte");
        tfAbfahrt = new TextField();
        tfNach = new TextField();
        tfZugnummer = new TextField();
        tfGleis = new TextField();
        taZwischenhalte = new TextArea();
    }

    private void layoutControls() {
        // Top - Bereich einbinden
        top.getChildren().addAll(speichern, neu, löschen, undo, redo, zug, anzeigeTafel, pause, start, suche, language);
        setTop(top);

        // Horizontales splitting der Splitpane
        splitPane.setOrientation(Orientation.HORIZONTAL);

        // Splitpane in das Zentrum vom Bodrderpane einbinden
        setCenter(splitPane);

        // linke Seite befüllen
        leftSide.getColumns().addAll(led, departure, to, track);

        //  rechte Seite - Tableview Kollonnen bestimmen
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        rightSide.getColumnConstraints().addAll(cc, cc, cc, cc); // alle Spalten sollen wachsen
        // rechte Seite - Tableview Zeilen bestimmen
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        rightSide.getRowConstraints().addAll(rc, rc, rc, rc, rc, rc); // Alle Zeilen sollen wachsen

        // rechte Seite layouten
        rightSide.add(abfahrt, 0, 0);
        rightSide.add(nach, 0, 1);
        rightSide.add(zugnummer, 0, 2);
        rightSide.add(gleis, 0, 3);
        rightSide.add(zwischehalte, 0, 4);
        rightSide.add(tfAbfahrt, 1, 0);
        rightSide.add(tfNach, 1, 1);
        rightSide.add(tfZugnummer, 1, 2);
        rightSide.add(tfGleis, 1, 3);
        rightSide.add(taZwischenhalte, 1, 4);

        // Rechte und linke Seite in Splitpane implementieren
        splitPane.getItems().addAll(leftSide, rightSide);
        splitPane.setDividerPosition(0, 0.1);
    }

    private void addEventHandlers() {
        speichern.setOnAction(event -> pm.save());
        neu.setOnAction(event -> pm.addNewDeparture(leftSide));
        löschen.setOnAction(event -> pm.removeDeparture(leftSide));

        //Sprache nach ausgewähltem Modus ändern
        language.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Locale>() {
            @Override
            public void changed(ObservableValue<? extends Locale> observable, Locale oldValue, Locale newValue) {
                languagehandler.changeLanguage(newValue);
            }
        });
    }

    private void addBindings() {
    }

    private void addLanguageChanger() {
        language.getSelectionModel().select(0);

    }
}



