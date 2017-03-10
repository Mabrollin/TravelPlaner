/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.light.gui;

import TravelPlaner.TrafficMap;
import TravelPlaner.TravelOption;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import sl.light.gui.components.Route;
import sl.light.gui.components.Station;
import sl.light.gui.dialogs.newRouteDialog;
import sl.light.gui.dialogs.newStationDialog;

/**
 *
 * @author MARBRO02
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Pane mapPane;
    @FXML
    private Pane planerPane;
    @FXML
    private ComboBox departureComboBox;
    @FXML
    private ComboBox destinationComboBox;
    @FXML
    private MenuItem addStationMenuItem;
    @FXML
    private MenuItem addRouteMenuItem;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private TextField timeField;
    @FXML
    private Slider hourSlider;
    @FXML
    private Slider minuteSlider;
    @FXML
    private Button getTripButton;
    @FXML
    private TextArea tripSuggestionField;

    private final Line tempLine = new Line();

    private boolean isPlaceMode;
    private PlaceMode placeMode;
    private final Station[] routeStations = new Station[2];

    private final TrafficMap trafficMap = new TrafficMap();
    private final Set<String> stopNames = new HashSet<>();
    private final Alert alert = new Alert(AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert.setTitle("Naming Error");
        alert.setHeaderText("There is already a Stop with that name!");
        ChangeListener<Number> timeUpdate = (ObservableValue<? extends Number> action, Number oldValue, Number newValue) -> {

            timeField.setText(((int) hourSlider.getValue() < 10 ? "0" + (int) hourSlider.getValue() : (int) hourSlider.getValue()) + ":" + ((int) minuteSlider.getValue() < 10 ? "0" + (int) minuteSlider.getValue() : (int) minuteSlider.getValue()));
        };
        hourSlider.valueProperty().addListener(timeUpdate);
        minuteSlider.valueProperty().addListener(timeUpdate);

        toggleButton.setOnAction(action -> {
            if (toggleButton.getText().equals("Departure")) {
                toggleButton.setText("Arrival");
            } else {
                toggleButton.setText("Departure");
            }
        });
        getTripButton.setOnAction(action -> {
            LocalTime time = LocalTime.parse(timeField.getText());
            String departure = (String) departureComboBox.getValue();
            String destination = (String) destinationComboBox.getValue();
            TravelOption travelOption = trafficMap.getTravelSuggestions(departure, destination, time);
            if (travelOption == null) {
                tripSuggestionField.setText("No possible trips");

            } else {
                tripSuggestionField.setText(travelOption.toString());
            }
        });

        mainAnchor.setOnKeyTyped(value -> {
            System.out.println("handling button action" + value.getEventType());

            switch (value.getCharacter()) {
                case "s":
                    enterAddStationMode();
                    break;
                case "r":
                    enterAddRouteMode();
                    break;
            }
        });

        tempLine.setVisible(false);
        mapPane.getChildren().add(tempLine);
        mapPane.setOnMouseClicked(action -> {
            double x = action.getX();
            double y = action.getY();
            switch (action.getButton()) {
                case PRIMARY:
                    if (isPlaceMode) {
                        handlePlaceMode(x, y);
                    } else {

                    }
                    break;
            }

        });
        mapPane.setOnMouseMoved(action -> {
            double x = action.getX();
            double y = action.getY();
            tempLine.setEndX(x + 1);
            tempLine.setEndY(y + 1);

        });

    }

    @FXML
    private void enterAddStationMode() {
        System.out.println("Enter add station mode");
        placeMode = PlaceMode.STATION;
        isPlaceMode = true;

    }

    @FXML
    private void enterAddRouteMode() {
        System.out.println("Enter route mode");
        placeMode = PlaceMode.ROUTE_START;
        isPlaceMode = true;
        Station.setSelectable(isPlaceMode);
    }

    private void handlePlaceMode(double x, double y) {
        switch (placeMode) {
            case STATION:
                String name = newStationDialog.runAndFetch();
                while (stopNames.contains(name)) {
                    alert.showAndWait();
                    name = newStationDialog.runAndFetch();
                }
                stopNames.add(name);
                Station station = new Station(x, y, name);
                destinationComboBox.getItems().add(name);
                departureComboBox.getItems().add(name);
                ObservableList<Node> children = mapPane.getChildren();
                children.add(station);
                children.add(station.getNameField());
                trafficMap.add(station.getStop());
                isPlaceMode = false;

                break;
            case ROUTE_START:
                if (Station.lastClick != null) {
                    tempLine.setVisible(true);
                    routeStations[0] = Station.lastClick;

                    tempLine.setStartX(Station.lastClick.getCenterX());
                    tempLine.setStartY(Station.lastClick.getCenterY());
                    placeMode = PlaceMode.ROUTE_END;
                    Station.lastClick.deselect();
                }
                break;
            case ROUTE_END:
                if (Station.lastClick != null) {
                    tempLine.setVisible(false);
                    routeStations[1] = Station.lastClick;
                    Route route = new Route(routeStations, newRouteDialog.runAndFetch());
                    mapPane.getChildren().add(route);
                    routeStations[0].toFront();
                    routeStations[1].toFront();
                    Station.setSelectable(isPlaceMode);
                    Station.lastClick.deselect();
                    System.out.println("Succesfully leaving route mode");
                    isPlaceMode = false;
                }
                break;
        }

    }

}
