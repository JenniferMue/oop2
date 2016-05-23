package ch.fhnw.oop2.DepatureApp;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;


/**
 * Created by Jennifer Müller on 24.04.2016.
 */

public class DepatureAppUI extends BorderPane {

    /*------------------------
        Attribute
     -------------------------*/

    private final Depature.DepatureAppPM pm;




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
    private TextField suche;

    // Center
    private SplitPane splitPane;
    public TableView<Depature> leftSide;    // linke Seite für das Center
    private GridPane rightSide;             // rechte Seite für das Center

    //Linke Seite (leftSide)
    private TableColumn status; // TODO Object Bild einfügen
    private TableColumn<Depature,String> departure;
    private TableColumn <Depature,String> to;
    private TableColumn <Depature, String> track;


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


    public DepatureAppUI(Depature.DepatureAppPM pm){
        this.pm=pm;
        initializeControls();
        layoutControls();
        addEventHandlers();
        addValueChangedListeners();
        addBindings();
    }




    private void addValueChangedListeners() {
    }

     /*------------------------
        Methoden
     -------------------------*/

    private void initializeControls() {
        // Top
        FontAwesomeIconView saveIcon = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
        speichern = new Button("",saveIcon);
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
        anzeigeTafel = new Button("",anzeigeIcon);
        anzeigeIcon.setId("Icon");
        FontAwesomeIconView pauseIcon = new FontAwesomeIconView(FontAwesomeIcon.PAUSE);
        pause = new Button("", pauseIcon);
        pauseIcon.setId("Icon");
        FontAwesomeIconView startIcon = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        start = new Button("", startIcon);
        startIcon.setId("Icon");
        suche = new TextField("Suche");
        top =new HBox();


        // Center
        splitPane=new SplitPane();
        leftSide=new TableView(pm.getDepatures());
        rightSide=new GridPane();

        // Linker Bereich
        status = new TableColumn();
        departure = new TableColumn("Abfahrt");
        departure.setCellValueFactory(param -> param.getValue().uhrzeitProperty());
        to = new TableColumn("nach");
        to.setCellValueFactory(param -> param.getValue().inRichtungProperty());
        track = new TableColumn("Gleis");
        track.setCellValueFactory(param -> param.getValue().gleisNummerProberty());

        // Rechter Bereich
        abfahrt=new Label("Abfahrt");
        nach = new Label("nach");
        zugnummer=new Label("Zugnummer");
        gleis = new Label("Gleis");
        zwischehalte= new Label("Zwischenhalte");
        tfAbfahrt=new TextField();
        tfNach=new TextField();
        tfZugnummer= new TextField();
        tfGleis=new TextField();
        taZwischenhalte=new TextArea();

    }

    private void layoutControls() {
        //put top in Borderpane
        top.getChildren().addAll(speichern,neu, löschen,undo, redo,zug,anzeigeTafel, pause,start, suche);
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
        rightSide.getColumnConstraints().addAll(cc, cc, cc,cc); // alle Spalten sollen wachsen

        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        rightSide.getRowConstraints().addAll(rc, rc, rc,rc,rc,rc); // Alle Zeilen sollen wachsen

        // fill rightSide
        rightSide.add(abfahrt,0,0);
        rightSide.add(nach,0,1);
        rightSide.add(zugnummer,0,2);
        rightSide.add(gleis,0,3);
        rightSide.add(zwischehalte,0,4);
        rightSide.add(tfAbfahrt,1,0);
        rightSide.add(tfNach,1,1);
        rightSide.add(tfZugnummer,1,2);
        rightSide.add(tfGleis,1,3);
        rightSide.add(taZwischenhalte,1,4);

        // fill Splitpane with leftSide and rightSide
        splitPane.getItems().addAll(leftSide, rightSide);

    }

    private void addEventHandlers() { // TODO Eventhandlers
        speichern.setOnAction(event -> pm.save());

        neu.setOnAction(event -> pm.addNewDeparture()); // auf der rechten Seite immer Methoden auf dem Mode

    }


    private void addBindings() { // TODO Bindings noch richtigt einstellen
        tfAbfahrt.textProperty().bindBidirectional(pm.getSelectedDeparture().uhrzeitProperty());
        tfGleis.textProperty().bindBidirectional(pm.getSelectedDeparture().gleisProperty());
        tfZugnummer.textProperty().bindBidirectional(pm.getSelectedDeparture().zugnummerProperty());
        taZwischenhalte.textProperty().bindBidirectional(pm.getSelectedDeparture().ueberProperty());
        tfNach.textProperty().bindBidirectional(pm.getSelectedDeparture().inRichtungProperty());

    }

}

