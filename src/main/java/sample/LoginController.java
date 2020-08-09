package sample;

import com.dev.foodreservation.database.LoginHandler;
import com.dev.foodreservation.database.utilities.FieldController;
import com.dev.foodreservation.database.utilities.SharedPreferences;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginController implements Initializable {

    @FXML
    JFXRadioButton userType;
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    @FXML
    JFXButton loginButton;

    private ExecutorService exec;

    double xOffset, yOffset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

    @FXML
    private void onLogin() {
        loginButton.setDisable(true);
        try {
            if (userType.isSelected()) {
                long user = (long) getFieldValue(username,
                        FieldController.LONG);
                String pass = (String) getFieldValue(password,
                        FieldController.PASSWORD);
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return new LoginHandler()
                                .studentLogin(user, pass);
                    }
                };
                task.setOnFailed(e -> task.getException().printStackTrace());
                task.setOnSucceeded(e -> {
                    Boolean taskValue = task.getValue();
                    if (taskValue) {
                        try {
                            SharedPreferences.add("loggedInStudent", user);
                            Stage currentStage =
                                    (Stage) loginButton.getScene()
                                            .getWindow();
                            currentStage.close();
                            studentStage();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.out.println("Right!");
                    } else System.out.println("NO!");
                    loginButton.setDisable(false);
                });
                exec.execute(task);
            } else {
                String user = (String) getFieldValue(username,
                        FieldController.PASSWORD);
                String pass = (String) getFieldValue(password,
                        FieldController.PASSWORD);
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return new LoginHandler()
                                .adminLogin(user, pass);
                    }
                };
                task.setOnFailed(e -> task.getException().printStackTrace());
                task.setOnSucceeded(e -> {
                    Boolean taskValue = task.getValue();
                    if (taskValue) {
                        try {
                            Stage currentStage =
                                    (Stage) loginButton.getScene()
                                            .getWindow();
                            currentStage.close();
                            adminStage();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.out.println("Right!");
                    } else System.out.println("NO!");
                    loginButton.setDisable(false);
                });
                exec.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void studentStage() throws IOException {
        URL url = new File("src/main/java/sample/student.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Hello World");
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    private void adminStage() throws IOException {
        URL url = new File("src/main/java/sample/admin.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Hello World");
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    private Object getFieldValue(TextField textField,
                                 int fieldController) {
        if (textField == null) return null;
        String value = textField.getText().trim();
        if (fieldController == FieldController.STRING) {
            for (char c : value.toCharArray())
                if (!Character.isAlphabetic(c) && !Character.isSpaceChar(c))
                    throw new InputMismatchException();
            return value.trim();
        } else if (fieldController == FieldController.BYTE) {
            try {
                return Byte.parseByte(value);
            } catch (NumberFormatException e) {
                return new InputMismatchException();
            }
        } else if (fieldController == FieldController.INTEGER) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return new InputMismatchException();
            }
        } else if (fieldController == FieldController.LONG) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return new InputMismatchException();
            }
        } else if (fieldController == FieldController.DOUBLE) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return new InputMismatchException();
            }
        } else if (fieldController == FieldController.PASSWORD) {
            try {
                return value.trim();
            } catch (Exception e) {
                return new InputMismatchException();
            }
        } else if (fieldController == FieldController.NUMERIC) {
            try {
                for (char c : value.toCharArray())
                    if (!Character.isDigit(c))
                        throw new InputMismatchException();
                return value.trim();
            } catch (Exception e) {
                return new InputMismatchException();
            }
        } else if (fieldController == FieldController.STRING_SPACE) {
            try {
                for (char c : value.toCharArray())
                    if (!Character.isAlphabetic(c))
                        if (!Character.isSpaceChar(c))
                            throw new InputMismatchException();
                return value.trim();
            } catch (Exception e) {
                return new InputMismatchException();
            }
        }
        return null;
    }
}