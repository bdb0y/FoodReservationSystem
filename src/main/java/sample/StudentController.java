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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
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
    JFXComboBox mealCalendarKitchenFilter;
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

    private SelectionModel<Tab> mainTabSelection;
    private SelectionModel<Tab> walletTabSelection;
    private SelectionModel<Tab> accountTabSelection;

    private ExecutorService exec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainTabSelection = mainTabPane.getSelectionModel();
        walletTabSelection = walletTabPane.getSelectionModel();
        accountTabSelection = accountTabPane.getSelectionModel();


        SharedPreferences.add("loggedInStudent", (long) 96112);

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
                return new KitchenDAO().idGet(-1);
            }
        };

        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> {
            List<Kitchen> taskKitchens = task.getValue();
            mealCalendarKitchenFilter
                    .getItems().addAll(taskKitchens);
            mealCalendarKitchenFilter.getSelectionModel()
                    .select(0);
            selectedKitchen
                    .setText(String.valueOf(mealCalendarKitchenFilter
                            .getSelectionModel()
                            .getSelectedItem().toString()));
            setupMealCalendar();
        });
        exec.execute(task);
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
                        1,
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
        int total = 0;
        switch (selectedType) {
            case 0:
                id = setupMealCalendar.getBmId();
                total = setupMealCalendar.getTotalBF();
                break;
            case 1:
                id = setupMealCalendar.getLmId();
                total = setupMealCalendar.getTotalL();
                break;
            case 2:
                id = setupMealCalendar.getDmId();
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
        } else {
            mealCalendarName.getSelectionModel().select(theOne);
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
                        (SetupMealCalendar) mealCalendarTableView
                                .getSelectionModel().getSelectedItem()
                );
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
    }

    @FXML
    private void onWalletSection() {
        mainTabSelection.select(1);
        onWalletInfo();
    }

    @FXML
    private void onAccountSection() {
        mainTabSelection.select(2);
        accountTabSelection.select(0);
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

    private void addComboItems(JFXComboBox comboBox,
                               String... items) {
        comboBox.getItems().addAll(items);
    }
}
