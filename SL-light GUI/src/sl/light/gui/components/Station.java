/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.light.gui.components;

import TravelPlaner.Stop;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author MARBRO02
 */
public class Station extends Circle {

    private static final double RADIUS = 7;
    private static final Color BASE_COLOR = Color.BLUE;
    private static final Color SELECTED_COLOR = Color.DARKBLUE;
    private static final double X_TEXT_OFFSET = 15;
    private static final double Y_TEXT_OFFSET = 15;
    private static boolean selectable;

    public static void setSelectable(boolean placeMode) {
        selectable = placeMode;
    }

    private final String name;
    private final Text nameField;
    private boolean expanded = false;
    public static Station lastClick;
    private Stop stop;

    public Station(double x, double y, String name) {
        stop = new Stop((long) getCenterX(), (long) getCenterX(), name);
        this.name = name;
        nameField = new Text(name);
        setRadius(RADIUS);
        setCenterX(x);
        setCenterY(y);
        setFill(BASE_COLOR);
        nameField.setX(x + X_TEXT_OFFSET);
        nameField.setY(y - Y_TEXT_OFFSET);
        updateDescription();

        this.setOnMouseClicked((MouseEvent action) -> {
            switch (action.getButton()) {
                case PRIMARY:
                    if (selectable) {
                        if (lastClick != null) {
                            lastClick.deselect();
                        }
                        select();
                    }
                    break;
                case SECONDARY:
                    toggleExpanded();
                    break;

            }

        });

    }

    private void toggleExpanded() {
        expanded = !expanded;
        updateDescription();

    }

    public Text getNameField() {
        return nameField;
    }

    private void updateDescription() {
        nameField.setVisible(expanded);
    }

    public void deselect() {
        setFill(BASE_COLOR);
        lastClick = null;
    }

    private void select() {
        lastClick = this;
        setFill(SELECTED_COLOR);
    }

    public Stop getStop() {
        return stop;
    }
}
