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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author MARBRO02
 */
public class newRouteDialog {

    private final static Dialog<String[]> dialog;

    static {
        // Create the custom dialog.
        dialog = new Dialog<>();
        dialog.setTitle("New Route");
        dialog.setHeaderText("Enter departures and arrivals");

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Done", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Buss 317, Metro Line");
        TextArea departures = new TextArea();
        departures.setPromptText("00:00\r\n00:00+15*7\r\n00:00+15->12:30");

        departures.setPrefSize(150, 300);
        TextArea arrivals = new TextArea();
        arrivals.setPromptText("00:20\r\n00:20\r\n00:20");
        arrivals.setPrefSize(150, 300);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Departures:"), 0, 1);
        grid.add(departures, 0, 2);
        grid.add(new Label("Arrivals:"), 1, 1);
        grid.add(arrivals, 1, 2);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        //   loginButton.setDisable(true);

        dialog.getDialogPane().setContent(grid);

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String stringData[] = {name.getText(),
                     departures.getText(),
                     arrivals.getText()};
                return stringData;

            }
            return null;
        });

    }

    public static String[] runAndFetch() {
        Optional<String[]> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result.get();

        } else {
            return null;
        }

    }
}
