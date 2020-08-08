package com.dev.foodreservation.database.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class AlertHandler {

    private Alert.AlertType type;
    private String message;

    public AlertHandler(Alert.AlertType type, String message) {
        this.type = type;
        this.message = message;

        Alert alert = new Alert(type,
                message,
                ButtonType.OK);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.showAndWait();
    }
}
