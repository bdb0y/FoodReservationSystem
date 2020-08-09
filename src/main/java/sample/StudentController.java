package sample;


import com.dev.foodreservation.database.*;
import com.dev.foodreservation.database.utilities.AlertHandler;
import com.dev.foodreservation.database.utilities.FieldController;
import com.dev.foodreservation.database.utilities.FieldHandler;
import com.dev.foodreservation.database.utilities.SharedPreferences;
import com.dev.foodreservation.objects.*;
import com.github.mfathi91.time.PersianDate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentController implements Initializable {

    @FXML
    Label sectionTitle;
    @FXML
    JFXButton mealSection;
    @FXML
    JFXButton walletSection;
    @FXML
    JFXButton accountSection;
    @FXML
    TabPane mainTabPane;
    @FXML
    TabPane walletTabPane;
    @FXML
    TabPane accountTabPane;
    @FXML
    Label accountFirstName;
    @FXML
    Label accountLastName;
    @FXML
    Label accountSex;
    @FXML
    Label accountMealLimit;
    @FXML
    JFXTextField currentPassword;
    @FXML
    JFXTextField newPassword;
    @FXML
    JFXTextField confirmPassword;
    @FXML
    Label walletBalance;
    @FXML
    TableView<WalletTransaction> walletTransactions;
    @FXML
    JFXTextField chargeWalletAmount;
    @FXML
    JFXTextField chargeWalletCardNumber;
    @FXML
    JFXPasswordField chargeWalletPass;
    @FXML
    JFXPasswordField chargeWalletCVV2;
    @FXML
    TableView<SetupMealCalendar> mealCalendarTableView;
    @FXML
    Label mealCalendarFromToDate;
    @FXML
    JFXButton preButton;
    @FXML
    JFXButton nextButton;
    @FXML
    JFXComboBox<Kitchen> mealCalendarKitchenFilter;
    @FXML
    JFXComboBox mealCalendarMealType;
    @FXML
    Label selectedMealType;
    @FXML
    Label selectedDate;
    @FXML
    Label selectedDay;
    @FXML
    Label selectedKitchen;
    @FXML
    JFXComboBox<Meal> mealCalendarName;
    @FXML
    Label reservationStatus;
    @FXML
    JFXButton reserveButton;
    @FXML
    JFXButton cancelButton;

    @FXML
    JFXButton logoutButton;
    @FXML
    Label dateInfo;
    @FXML
    Label studentRollId;

    private SelectionModel<Tab> mainTabSelection;
    private SelectionModel<Tab> walletTabSelection;
    private SelectionModel<Tab> accountTabSelection;

    private ExecutorService exec;

    private Time time;
    private Date date;

    private int mainSelectedTab = 0;
    private double xOffset;
    private double yOffset;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainTabSelection = mainTabPane.getSelectionModel();
        walletTabSelection = walletTabPane.getSelectionModel();
        accountTabSelection = accountTabPane.getSelectionModel();

        time = Time.valueOf(LocalTime.now());
        date = Date.valueOf(PersianDate.now().toString());
        setDayAndDate();
        studentRollId.setText(String.valueOf(
                (long) SharedPreferences
                        .get("loggedInStudent"))
        );

        mealSection.setStyle("-fx-background-color: white;" +
                "-fx-text-fill: #0f4c75");

        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
        resolveStudentInformation();
        setupWalletInfoTableView();
        mealCalendarTableViewInitializer();
        getThisWeekRange();
        mealCalendarKitchenFilterInjection();
        mealCalendarMealTypeFilterInjection();
    }

    private void setDayAndDate() {
        PersianDate today = PersianDate.now();
        String dayAndDate = today.getDayOfWeek() + " " +
                today.toString().replaceAll("-", "/");
        dateInfo.setText(dayAndDate);
    }

    // Start------------ Meal -------------

    private void mealCalendarTableViewInitializer() {
        TableColumn<SetupMealCalendar, Date> dateColumn =
                new TableColumn<>("Date");
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        dateColumn.setReorderable(false);
        dateColumn.setSortable(false);

        TableColumn<SetupMealCalendar, String> dayColumn =
                new TableColumn<>("Day");
        dayColumn.setCellValueFactory(
                new PropertyValueFactory<>("day"));
        dayColumn.setReorderable(false);
        dayColumn.setSortable(false);

        TableColumn<SetupMealCalendar, String> breakfastColumn =
                new TableColumn<>("Breakfast");
        breakfastColumn.setCellValueFactory(
                new PropertyValueFactory<>("breakfastName"));
        breakfastColumn.setReorderable(false);
        breakfastColumn.setSortable(false);

        TableColumn<SetupMealCalendar, String> launchColumn =
                new TableColumn<>("Launch");
        launchColumn.setCellValueFactory(
                new PropertyValueFactory<>("launchName"));
        launchColumn.setReorderable(false);
        launchColumn.setSortable(false);

        TableColumn<SetupMealCalendar, String> dinnerColumn =
                new TableColumn<>("Dinner");
        dinnerColumn.setCellValueFactory(
                new PropertyValueFactory<>("dinnerName"));
        dinnerColumn.setReorderable(false);
        dinnerColumn.setSortable(false);

        mealCalendarTableView.getColumns().addAll(
                dateColumn, dayColumn, breakfastColumn,
                launchColumn, dinnerColumn
        );
    }

    private void getThisWeekRange() {
        PersianDate date = PersianDate.now();
        int todayValue = date.getDayOfWeek().getValue() + 2;
        if (todayValue == 8 || todayValue == 9) todayValue -= 7;
        PersianDate from = date.plusDays(-(todayValue - 1));
        PersianDate to = date.plusDays((7 - todayValue));
        SharedPreferences.add("fromDate", from);
        SharedPreferences.add("toDate", to);
        setFromToDateRange();
    }

    private void setFromToDateRange() {
        PersianDate fromDate =
                (PersianDate) SharedPreferences.get("fromDate"),
                toDate =
                        (PersianDate) SharedPreferences.get("toDate");
        String result = fromDate.toString().replaceAll("-", "/")
                + " - " + toDate.toString().replaceAll("-", "/");
        mealCalendarFromToDate.setText(result);
    }

    private void mealCalendarKitchenFilterInjection() {
        Task<List<Kitchen>> task = new Task<>() {
            @Override
            public List<Kitchen> call() throws Exception {
                return new KitchenDAO().getStudentKitchen(
                        (long) SharedPreferences
                                .get("loggedInStudent")
                );
            }
        };

        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> {
            List<Kitchen> taskKitchens = task.getValue();
            mealCalendarKitchenFilter
                    .getItems().addAll(taskKitchens);
            if (taskKitchens.size() > 0) {
                mealCalendarKitchenFilter.getSelectionModel()
                        .select(0);
                selectedKitchen
                        .setText(String.
                                valueOf(mealCalendarKitchenFilter
                                .getSelectionModel()
                                .getSelectedItem().toString()));
            }
        });
        exec.execute(task);
    }

    @FXML
    private void onMealCalendarKitchenFilter() {
        Kitchen kitchen = mealCalendarKitchenFilter
                .getSelectionModel()
                .getSelectedItem();
        if (kitchen != null) {
            selectedKitchen
                    .setText(String.valueOf(kitchen.getName()));
            setupMealCalendar();
        }
    }

    @FXML
    private void nextWeekRange() {
        PersianDate fromDate =
                (PersianDate) SharedPreferences.get("toDate");
        PersianDate toDate;
        fromDate = fromDate.plusDays(1);
        toDate = fromDate.plusDays(6);
        SharedPreferences.add("fromDate", fromDate);
        SharedPreferences.add("toDate", toDate);
        setFromToDateRange();
        setupMealCalendar();
    }

    @FXML
    private void previousWeekRange() {
        PersianDate fromDate =
                (PersianDate) SharedPreferences.get("fromDate");
        PersianDate toDate;
        toDate = fromDate.plusDays(-1);
        fromDate = fromDate.plusDays(-7);
        SharedPreferences.add("fromDate", fromDate);
        SharedPreferences.add("toDate", toDate);
        setFromToDateRange();
        setupMealCalendar();
    }

    @FXML
    private void onMealCalendarTableView() {
        SetupMealCalendar setupMealCalendar =
                mealCalendarTableView.getSelectionModel()
                        .getSelectedItem();
        if (setupMealCalendar != null) {
            setSelectedMealCalendarDateInfo(setupMealCalendar.getDay(),
                    setupMealCalendar.getDate());
            selectMeal(setupMealCalendar);
        }
    }

    private void setupMealCalendar() {
        mealCalendarTableView.getItems().clear();
        preButton.setDisable(true);
        nextButton.setDisable(true);
        Task<List<SetupMealCalendar>> task = new Task<>() {
            @Override
            public List<SetupMealCalendar> call() throws Exception {
                PersianDate fromDate = (PersianDate) SharedPreferences.get("fromDate"),
                        toDate = (PersianDate) SharedPreferences.get("toDate");
                return new MealCalendarDAO().getMealCalendar(
                        mealCalendarKitchenFilter
                                .getSelectionModel()
                                .getSelectedItem().getId(),
                        Date.valueOf(fromDate.toString()),
                        Date.valueOf(toDate.toString())
                );
            }
        };

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            preButton.setDisable(false);
            nextButton.setDisable(false);
        });
        task.setOnSucceeded(e -> {
            List<SetupMealCalendar> taskCalendar = task.getValue();
            mealCalendarTableView
                    .getItems().addAll(taskCalendar);
            setMealCalendarMealType();
            preButton.setDisable(false);
            nextButton.setDisable(false);
        });
        exec.execute(task);
    }

    private void mealCalendarMealTypeFilterInjection() {
        addComboItems(mealCalendarMealType,
                "Breakfast", "Launch", "Dinner");
        mealCalendarMealType.getSelectionModel().select(0);
    }

    @FXML
    private void setMealCalendarMealType() {
        switch (mealCalendarMealType.getSelectionModel().getSelectedIndex()) {
            case 0:
                selectedMealType.setText("BREAKFAST");
                break;
            case 1:
                selectedMealType.setText("LAUNCH");
                break;
            case 2:
                selectedMealType.setText("DINNER");
                break;
        }
        setMealNameMealCalendar();
    }

    private void selectMeal(SetupMealCalendar setupMealCalendar) {
        int selectedType = mealCalendarMealType
                .getSelectionModel().getSelectedIndex();
        int id = -1;
        int mealCalendarId = -1;
        int total = 0;
        switch (selectedType) {
            case 0:
                id = setupMealCalendar.getBmId();
                mealCalendarId = setupMealCalendar.getBreakfastMealId();
                total = setupMealCalendar.getTotalBF();
                break;
            case 1:
                id = setupMealCalendar.getLmId();
                mealCalendarId = setupMealCalendar.getLaunchMealId();
                total = setupMealCalendar.getTotalL();
                break;
            case 2:
                id = setupMealCalendar.getDmId();
                mealCalendarId = setupMealCalendar.getDinnerMealId();
                total = setupMealCalendar.getTotalD();
                break;
        }
        List<Meal> meals =
                mealCalendarName.getItems();
        Meal theOne = null;
        for (Meal m : meals)
            if (m.getId() == id)
                theOne = m;
        if (theOne == null) {
            mealCalendarName.getSelectionModel()
                    .clearSelection();
            reservationStatus.setText("-");
            reserveButton.setDisable(true);
            cancelButton.setDisable(true);
        } else {
            mealCalendarName.getSelectionModel().select(theOne);
            SharedPreferences.add("mealCalendarId",
                    mealCalendarId);
            checkIfMealReserved(mealCalendarId);
        }
    }

    private void checkIfMealReserved(int id) {
        System.out.println(id);
        System.out.println(SharedPreferences.get("loggedInStudent"));

        try {
            Task<List<MealReservation>> task = new Task<>() {
                @Override
                public List<MealReservation> call() throws Exception {
                    return new MealDAO().checkIfReserved(
                            (long) SharedPreferences.get("loggedInStudent"),
                            id
                    );
                }
            };

            task.setOnFailed(e -> {
                task.getException().printStackTrace();
            });
            task.setOnSucceeded(e -> {
                List<MealReservation> taskMeal = task.getValue();
                if (taskMeal.size() > 0) {
                    SharedPreferences.add("mealReservationId",
                            taskMeal.get(0).getId());
                    reservationStatus.setText("RESERVED");
                    reserveButton.setDisable(true);
                    cancelButton.setDisable(false);
                } else {
                    reservationStatus.setText("NOT RESERVED");
                    cancelButton.setDisable(true);
                    reserveButton.setDisable(false);
                }
            });
            exec.execute(task);
        } catch (Exception e) {

        }
    }

    private void setSelectedMealCalendarDateInfo(String day, Date date) {
        selectedDay.setText(day);
        selectedDate.setText(date
                .toString().replaceAll("-", "/"));
    }

    private void setMealNameMealCalendar() {
        mealCalendarName.getItems().clear();
        Task<List<Meal>> task = new Task<>() {
            @Override
            public List<Meal> call() throws Exception {
                int selectedType =
                        mealCalendarMealType
                                .getSelectionModel().getSelectedIndex();
                return new MealDAO().typeGet(selectedType);
            }
        };

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });
        task.setOnSucceeded(e -> {
            List<Meal> taskMeal = task.getValue();
            mealCalendarName
                    .getItems().addAll(taskMeal);
            if (mealCalendarTableView.getSelectionModel()
                    .getSelectedItem() == null)
                mealCalendarTableView.getSelectionModel()
                        .select(0);
            onMealCalendarTableView();
            if (mealCalendarTableView.getSelectionModel()
                    .getSelectedItem() != null)
                selectMeal(
                        mealCalendarTableView
                                .getSelectionModel()
                                .getSelectedItem()
                );
        });
        exec.execute(task);
    }

    @FXML
    private void onReserveButton() {
        time = Time.valueOf(LocalTime.now());
        System.out.println(
                SharedPreferences.get("mealCalendarId")
        );
        try {
            Task<Boolean> task = new Task<>() {
                @Override
                public Boolean call() throws Exception {
                    return new MealDAO().reserve(
                            (long) SharedPreferences.get("loggedInStudent"),
                            (int) SharedPreferences.get("mealCalendarId"),
                            date,
                            time
                    );
                }
            };

            task.setOnFailed(e -> task.getException().printStackTrace());
            task.setOnSucceeded(e -> {
                Boolean taskStudent = task.getValue();
                if (taskStudent) {
                    System.out.println("reserved");
                    new AlertHandler(Alert.AlertType.NONE,
                            "Meal reserved!");
                    setupMealCalendar();
                } else {
                    new AlertHandler(Alert.AlertType.ERROR,
                            "Not enough balance or date is incorrect!");
                }
            });
            exec.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancelButton() {
        time = Time.valueOf(LocalTime.now());
        Task<Boolean> task = new Task<>() {
            @Override
            public Boolean call() throws Exception {
                return new MealDAO().cancel(
                        (int) SharedPreferences.get("mealReservationId"),
                        date,
                        time
                );
            }
        };

        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> {
            Boolean taskStudent = task.getValue();
            if (taskStudent) {
                new AlertHandler(Alert.AlertType.NONE,
                        "Meal canceled successfully!");
                setupMealCalendar();
            } else {
                new AlertHandler(Alert.AlertType.ERROR,
                        "Wrong date");
                setupMealCalendar();
            }
            ;

        });
        exec.execute(task);
    }

    // End-------------- Meal -------------

    // Start------------ Account > Personal information -------------

    private void resolveStudentInformation() {
        Task<List<Student>> task = new Task<>() {
            @Override
            public List<Student> call() throws Exception {
                return new StudentDAO().rollIdGet(
                        (long) SharedPreferences.get("loggedInStudent")
                );
            }
        };

        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> {
            List<Student> taskStudent = task.getValue();
            SharedPreferences.add("studentInformation",
                    taskStudent.get(0));
            setPersonalInformation();
        });
        exec.execute(task);
    }

    private void setPersonalInformation() {
        Student student =
                (Student) SharedPreferences.get("studentInformation");
        accountFirstName.setText(student.getFirstName());
        accountLastName.setText(student.getLastName());
        accountSex.setText(String.valueOf(student.getGenderString()));
        accountMealLimit.setText(String.valueOf(student.getMealLimit()));
    }

    // End-------------- Account > Personal information -------------

    // Start------------ Account > Change Password -------------

    @FXML
    private void changePasswordSubmit() {
        String currentPass = "",
                newPass = "",
                confirmPass = "";
        try {
            currentPass =
                    (String) getFieldValue(currentPassword,
                            FieldController.PASSWORD);
            newPass =
                    (String) getFieldValue(newPassword,
                            FieldController.PASSWORD);
            confirmPass =
                    (String) getFieldValue(confirmPassword,
                            FieldController.PASSWORD);

            if (newPass.equals(confirmPass)) {
                try {
                    String finalCurrentPass = currentPass;
                    String finalNewPass = newPass;
                    Task<Boolean> task = new Task<>() {
                        @Override
                        public Boolean call() throws Exception {
                            return new StudentDAO().changePassword(
                                    (long) SharedPreferences.get("loggedInStudent"),
                                    finalCurrentPass,
                                    finalNewPass
                            );
                        }
                    };

                    task.setOnFailed(e -> task.getException().printStackTrace());
                    task.setOnSucceeded(e -> {
                        Boolean result = task.getValue();
                        if (result) System.out.println("Password changed!");
                        else System.out.println("Your current password doesn't match!");
                    });
                    exec.execute(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else System.out.println("Your passwords don't match!");
        } catch (Exception e) {
            System.out.println("Fields should be filed properly");
        }
    }

    // Start------------ Wallet > Wallet Info -------------

    private void resolveWalletBalance() {
        try {
            Task<List<Wallet>> task = new Task<>() {
                @Override
                public List<Wallet> call() throws Exception {
                    return new WalletDAO().get(
                            (long) SharedPreferences.get("loggedInStudent")
                    );
                }
            };

            task.setOnFailed(e -> task.getException().printStackTrace());
            task.setOnSucceeded(e -> {
                Wallet result = task.getValue().get(0);
                walletBalance.setText(String.valueOf(result
                        .getBalance()));
            });
            exec.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resolveWalletTransactions() {
        walletTransactions.getItems().clear();
        try {
            Task<List<WalletTransaction>> task = new Task<>() {
                @Override
                public List<WalletTransaction> call() throws Exception {
                    return new WalletDAO().getTransactions(
                            (long) SharedPreferences.get("loggedInStudent")
                    );
                }
            };

            task.setOnFailed(e -> task.getException().printStackTrace());
            task.setOnSucceeded(e -> {
                List<WalletTransaction> result = task.getValue();
                walletTransactions.getItems().addAll(result);
            });
            exec.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupWalletInfoTableView() {
        TableColumn<WalletTransaction, Date> dateColumn =
                new TableColumn<>("Date");
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        TableColumn<WalletTransaction, Time> timeColumn =
                new TableColumn<>("Time");
        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("time"));

        TableColumn<WalletTransaction, Double> amountColumn =
                new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(
                new PropertyValueFactory<>("amount"));

        walletTransactions.getColumns().addAll(
                dateColumn, timeColumn, amountColumn
        );
    }

    // End-------------- Wallet > Wallet Info -------------

    // Start------------ Wallet > Charge Wallet -------------

    @FXML
    private void onChargeWalletSubmit() {
        try {
            double amount =
                    (double) getFieldValue(chargeWalletAmount,
                            FieldController.DOUBLE);
            String cardNumber =
                    (String) getFieldValue(chargeWalletCardNumber,
                            FieldController.NUMERIC);
            String pass =
                    (String) getFieldValue(chargeWalletPass,
                            FieldController.NUMERIC);
            String cvv =
                    (String) getFieldValue(chargeWalletCVV2,
                            FieldController.NUMERIC);

            try {
                Task<Boolean> task = new Task<>() {
                    @Override
                    public Boolean call() throws Exception {
                        return new WalletDAO().chargeUp(
                                (long) SharedPreferences.get("loggedInStudent"),
                                amount
                        );
                    }
                };

                task.setOnFailed(e -> task.getException().printStackTrace());
                task.setOnSucceeded(e -> {
                    Boolean result = task.getValue();
                    if (result) {
                        new AlertHandler(
                                Alert.AlertType.NONE,
                                "Wallet charged: " + amount

                        );
                        new FieldHandler().clearFields(
                                chargeWalletAmount,
                                chargeWalletCardNumber,
                                chargeWalletPass,
                                chargeWalletCVV2
                        );
                    } else System.out.println("Something went wrong!");
                });
                exec.execute(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Fields are empty");
        }
    }

    // End-------------- Wallet > Charge Wallet -------------

    // Start---------------- Logout -------------

    @FXML
    private void onLogout() {
        try {
            Stage currentStage =
                    (Stage) sectionTitle.getScene()
                            .getWindow();
            currentStage.close();
            loginStage();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void loginStage() throws IOException {
        URL url = new File("src/main/java/sample/login.fxml").toURI().toURL();
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

    // End---------------- Logout -------------

    //TabPane stuff

    @FXML
    private void onWalletInfo() {
        walletTabSelection.select(0);
        resolveWalletTransactions();
        resolveWalletBalance();
    }

    @FXML
    private void onChargeWallet() {
        walletTabSelection.select(1);
    }

    @FXML
    private void onPersonalInfo() {
        accountTabSelection.select(0);
    }

    @FXML
    private void onChangePassword() {
        accountTabSelection.select(1);
    }

    @FXML
    private void onMealSection() {
        mainTabSelection.select(0);
        sectionTitle.setText("Meal");
        changeBackgroundColor(0);
        mealSection.setStyle("-fx-background-color: white;" +
                "-fx-text-fill: #0f4c75");
        mainSelectedTab = 0;
    }

    @FXML
    private void onWalletSection() {
        mainTabSelection.select(1);
        onWalletInfo();
        sectionTitle.setText("Wallet");
        changeBackgroundColor(1);
        walletSection.setStyle("-fx-background-color: white;" +
                "-fx-text-fill: #0f4c75");
        mainSelectedTab = 1;
    }

    @FXML
    private void onAccountSection() {
        mainTabSelection.select(2);
        accountTabSelection.select(0);
        sectionTitle.setText("Account");
        changeBackgroundColor(2);
        accountSection.setStyle("-fx-background-color: white;" +
                "-fx-text-fill: #0f4c75");
        mainSelectedTab = 2;
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
                return null;
            }
        } else if (fieldController == FieldController.INTEGER) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (fieldController == FieldController.LONG) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (fieldController == FieldController.DOUBLE) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (fieldController == FieldController.PASSWORD) {
            try {
                return value.trim();
            } catch (Exception e) {
                return null;
            }
        } else if (fieldController == FieldController.NUMERIC) {
            try {
                for (char c : value.toCharArray())
                    if (!Character.isDigit(c))
                        throw new InputMismatchException();
                return value.trim();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private void changeBackgroundColor(int mainSelectedTab) {
        if (mainSelectedTab != this.mainSelectedTab) {
            if (this.mainSelectedTab == 0)
                mealSection.setStyle("-fx-background-color: transparent");
            else if (this.mainSelectedTab == 1)
                walletSection.setStyle("-fx-background-color: transparent");
            else if (this.mainSelectedTab == 2)
                accountSection.setStyle("-fx-background-color: transparent");
        }
    }

    private void addComboItems(JFXComboBox comboBox,
                               String... items) {
        comboBox.getItems().addAll(items);
    }
}