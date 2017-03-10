/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.light.gui.dialogs;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author MARBRO02
 */
public class newStationDialog {

    private final static TextInputDialog newStationDialog;

    static {
        newStationDialog = new TextInputDialog("Station");
        newStationDialog.setTitle("New Station");
        newStationDialog.setHeaderText("Enter the name of the new station");
        newStationDialog.setContentText("Name of Station: ");
    }

    public static String runAndFetch() {

// Traditional way to get the response value.
        Optional<String> result = newStationDialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }

    }
}
