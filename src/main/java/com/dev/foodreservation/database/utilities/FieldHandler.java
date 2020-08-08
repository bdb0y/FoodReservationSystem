package com.dev.foodreservation.database.utilities;

import javafx.scene.control.TextField;

public class FieldHandler {

    public void clearFields(TextField... textFields) {
        for (TextField textField : textFields)
            textField.clear();
    }
}
