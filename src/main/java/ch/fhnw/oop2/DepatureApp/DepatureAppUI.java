package ch.fhnw.oop2.DepatureApp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.util.Locale;


/**
 * Created by Jennifer Müller on 24.04.2016.
 */

public class DepatureAppUI extends BorderPane {

    /*------------------------
        Attribute
     -------------------------*/

    private final DepatureAppPM pm;


    // Attribute Buttons etc. setzen für Top Bereich
    private HBox top;
    private HBox center;
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

    // Center
    private SplitPane splitPane;
    public TableView<Departure> leftSide;    // linke Seite für das Center
    private GridPane rightSide;             // rechte Seite für das Center

    //Linke Seite (leftSide)
    private TableColumn status; // TODO Object Bild einfügen
    private TableColumn<Departure, String> departure;
    private TableColumn<Departure, String> to;
    private TableColumn<Departure, String> track;


    // Attribute für rechten Bereich (rightside)
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
        Konstruktor
     -------------------------*/

    //setterMethode für Labels
    public void setAbfahrt(String text){
        abfahrt.textProperty().setValue(text);
    }

    public void setGleis(String text){
        gleis.textProperty().setValue(text);
    }

    public void setZugnummer(String text){
        zugnummer.textProperty().setValue(text);
    }

    public void setZwischenhalte(String text){
        zwischehalte.textProperty().setValue(text);
    }

    public void setNach(String text){
        nach.textProperty().setValue(text);
    }


    public DepatureAppUI(DepatureAppPM pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        addEventHandlers();
        addValueChangedListeners();
        addBindings();
        language.getSelectionModel().select(0);
    }


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

     /*------------------------
        Methoden
     -------------------------*/

    private void initializeControls() {
        // Linker Bereich
        status = new TableColumn();
        departure = new TableColumn("Abfahrt");
        departure.setCellValueFactory(param -> param.getValue().uhrzeitProperty());
        to = new TableColumn("nach");
        to.setCellValueFactory(param -> param.getValue().inRichtungProperty());
        track = new TableColumn("Gleis");
        track.setCellValueFactory(param -> param.getValue().gleisNummerProberty());

        // Rechter Bereich
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

        // Top
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

        //construct languagehandler with ComboBox
        languagehandler = new LanguageHandler(this);
        language = new ComboBox<>(languagehandler.getList());
        suche = new TextField("Suche");
        top = new HBox();

        // Center
        splitPane = new SplitPane();
        leftSide = new TableView(pm.getDepatures());
        rightSide = new GridPane();


    }

    private void layoutControls() {
        //put top in Borderpane
        top.getChildren().addAll(speichern, neu, löschen, undo, redo, zug, anzeigeTafel, pause, start, language, suche);
        setTop(top);

        // Splitpane layout
        splitPane.setOrientation(Orientation.HORIZONTAL);

        // put Splitpane in center of Borderpane
        setCenter(splitPane);

        // fill leftside (TableView) of Splitpane
        leftSide.getColumns().addAll(status, departure, to, track);

        // rightSide (Gridpane)
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        rightSide.getColumnConstraints().addAll(cc, cc, cc, cc); // alle Spalten sollen wachsen

        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        rightSide.getRowConstraints().addAll(rc, rc, rc, rc, rc, rc); // Alle Zeilen sollen wachsen

        // fill rightSide
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

        // fill Splitpane with leftSide and rightSide
        splitPane.getItems().addAll(leftSide, rightSide);
    }

    private void addEventHandlers() {
        speichern.setOnAction(event -> pm.save());
        neu.setOnAction(event -> pm.addNewDeparture(leftSide)); // auf der rechten Seite immer Methoden auf dem Mode
        löschen.setOnAction(event -> pm.removeDeparture(leftSide));

        //change the language to new value
        language.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Locale>() {
            @Override
            public void changed(ObservableValue<? extends Locale> observable, Locale oldValue, Locale newValue) {
                languagehandler.changeLanguage(newValue);
            }
        });
    }

    private void addBindings() {
    }

}




