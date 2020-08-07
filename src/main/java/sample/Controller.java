package sample;

import com.dev.foodreservation.database.KitchenDAO;
import com.dev.foodreservation.database.MealCalendarDAO;
import com.dev.foodreservation.database.MealDAO;
import com.dev.foodreservation.database.StudentDAO;
import com.dev.foodreservation.database.utilities.FieldController;
import com.dev.foodreservation.database.utilities.SharedPreferences;
import com.dev.foodreservation.objects.Kitchen;
import com.dev.foodreservation.objects.Meal;
import com.dev.foodreservation.objects.SetupMealCalendar;
import com.dev.foodreservation.objects.Student;
import com.github.mfathi91.time.PersianDate;
import com.jfoenix.controls.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Controller implements Initializable {


    @FXML
    AnchorPane errorBadge;
    @FXML
    AnchorPane successBadge;
    @FXML
    private Label date;
    @FXML
    private JFXButton studentMenuSection;
    @FXML
    private JFXButton mealMenuSection;
    @FXML
    private JFXButton kitchenMenuSection;
    @FXML
    private JFXButton reportMenuSection;
    @FXML
    private TabPane mainTabPain;
    @FXML
    private TabPane studentTabPain;
    @FXML
    JFXButton addStudentTab;
    @FXML
    private JFXRadioButton male;
    @FXML
    private JFXRadioButton female;
    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField nationalIdField;
    @FXML
    private JFXTextField rollIdField;
    @FXML
    private JFXTextField mealLimitField;
    @FXML
    private JFXListView kitchenList;
    @FXML
    private JFXListView selectedKitchenList;
    @FXML
    JFXButton submitButton;
    @FXML
    JFXButton modifyStudentTab;
    @FXML
    TextField studentModifySearchField;
    @FXML
    JFXComboBox studentModifySearchFilterCombo;
    @FXML
    JFXButton studentModifySearchButton;
    @FXML
    TableView studentModifyTableView;
    @FXML
    JFXComboBox studentModificationFilter;
    @FXML
    JFXButton studentModificationSaveButton;
    @FXML
    private Label selectedStudentRollId;
    @FXML
    TabPane studentModificationTabPane;
    @FXML
    JFXRadioButton changeMale;
    @FXML
    JFXRadioButton changeFemale;
    @FXML
    JFXTextField changeFirstName;
    @FXML
    JFXTextField changeLastName;
    @FXML
    JFXTextField changeMealLimit;
    @FXML
    private TabPane mealTabPain;
    @FXML
    JFXButton addMealTab;
    @FXML
    JFXTextField nameField;
    @FXML
    JFXTextField priceField;
    @FXML
    JFXComboBox typeFilter;
    @FXML
    JFXButton addMealSubmitButton;
    @FXML
    JFXButton modifyMealTab;
    @FXML
    TextField mealModifySearchField;
    @FXML
    JFXButton mealModifySearchButton;
    @FXML
    TableView mealModifyTableView;
    @FXML
    JFXButton mealModifySaveButton;
    @FXML
    JFXTextField changeMealName;
    @FXML
    JFXTextField changeMealPrice;
    @FXML
    JFXComboBox changeMealTypeFilter;
    @FXML
    JFXButton mealCalendarTab;
    @FXML
    Label mealCalendarFromToDate;
    @FXML JFXButton preButton;
    @FXML JFXButton nextButton;
    @FXML
    JFXComboBox mealCalendarKitchenFilter;
    @FXML
    JFXComboBox mealCalendarMealType;
    @FXML
    TableView mealCalendarTableView;
    @FXML
    Label selectedMealType;
    @FXML
    Label selectedDate;
    @FXML
    Label selectedDay;
    @FXML
    Label selectedKitchen;
    @FXML
    JFXComboBox mealCalendarName;
    @FXML
    JFXTextField mealCalendarTotal;
    @FXML
    private TabPane kitchenTabPain;
    @FXML
    JFXButton addKitchenTab;
    @FXML
    JFXTextField kitchenName;
    @FXML
    JFXComboBox kitchenType;
    @FXML
    JFXButton kitchenSubmitButton;
    @FXML
    JFXButton modifyKitchenTab;
    @FXML
    TextField kitchenModifySearchField;
    @FXML
    JFXButton kitchenModifySearchButton;
    @FXML
    TableView kitchenModifyTableView;
    @FXML
    JFXTextField changeKitchenName;
    @FXML
    JFXComboBox changeKitchenType;
    @FXML
    JFXButton changeKitchenSaveButton;
    @FXML
    private TabPane reportTabPain;
    @FXML
    JFXButton studentReportTab;
    @FXML
    JFXComboBox studentReportFilter;
    @FXML
    JFXButton studentReportGetButton;
    @FXML
    TableView studentReportTableView;
    @FXML
    Label studentReportTotal;
    @FXML
    JFXButton mealReportTab;
    @FXML
    JFXComboBox mealReportFilter;
    @FXML
    JFXButton mealReportGetButton;
    @FXML
    TableView mealReportTableView;
    @FXML
    Label mealReportTotal;
    @FXML
    JFXButton kitchenReportTab;
    @FXML
    JFXComboBox kitchenReportFilter;
    @FXML
    JFXButton kitchenReportGetButton;
    @FXML
    TableView kitchenReportTableView;
    @FXML
    Label kitchenReportTotal;
    @FXML
    JFXButton transactionReportTab;
    @FXML
    private Label sectionTitle;


    private int mainSelectedTab = 0;

    private SelectionModel<Tab> mainTabSelection;
    private SelectionModel<Tab> studentTabSelection;
    private SelectionModel<Tab> mealTabSelection;
    private SelectionModel<Tab> kitchenTabSelection;
    private SelectionModel<Tab> reportTabSelection;
    private SingleSelectionModel<JFXComboBox> modificationSelection;
    private SingleSelectionModel<JFXComboBox> modifyFilterSelection;
    private SingleSelectionModel<Tab> modificationTabSelection;
    private TableView.TableViewSelectionModel<TableView> studentModifyTableViewSelection;

    private ExecutorService exec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        male.setSelected(true);
        mainTabSelection = mainTabPain.getSelectionModel();
        studentTabSelection = studentTabPain.getSelectionModel();
        mealTabSelection = mealTabPain.getSelectionModel();
        kitchenTabSelection = kitchenTabPain.getSelectionModel();
        reportTabSelection = reportTabPain.getSelectionModel();
        modificationSelection =
                studentModificationFilter.getSelectionModel();
        modificationTabSelection =
                studentModificationTabPane.getSelectionModel();
        modifyFilterSelection =
                studentModifySearchFilterCombo.getSelectionModel();
        studentModifyTableViewSelection =
                studentModifyTableView.getSelectionModel();

        kitchenList.getItems().add("Uni");
        kitchenList.getItems().add("Dorm");
        kitchenList.getItems().add("Dorm2");
        kitchenList.setOnMouseClicked(event -> {
            Object selectedItem = kitchenList.getSelectionModel().getSelectedItem();
            if (!selectedKitchenList.getItems().contains(selectedItem)
                    && selectedItem != null)
                selectedKitchenList.getItems().add(selectedItem);
            kitchenList.getSelectionModel().clearSelection();
        });
        selectedKitchenList.setOnMouseClicked(event -> {
            selectedKitchenList.getItems()
                    .remove(selectedKitchenList.getSelectionModel()
                            .getSelectedItem());
            selectedKitchenList.getSelectionModel().clearSelection();
        });


        setDayAndDate();
        studentMenuSection.setStyle("-fx-background-color: white;" +
                "-fx-text-fill: #0f4c75");
        sideMenuClickListeners();
        mealSectionClickListeners();
        kitchenSectionClickListeners();
        reportSectionClickListeners();
        modifyStudentFilterInjection();
        studentModifyTableViewInitializer();
        studentModifyTableViewItemListener();
        mealFilterInjection();
        mealModifyTableViewInitializer();
        mealModifyTableViewItemListener();
        kitchenTypeFilterInjection();
        kitchenModifyTableViewInitializer();
        kitchenModifyTableViewItemListener();
        reportFilterInjection();
        studentReportTableViewInitializer();
        mealReportTableViewInitializer();
        kitchenReportTableViewInitializer();
        studentClickListeners();
        getThisWeekRange();

        studentReportFilter.getSelectionModel()
                .select(0);
        mealReportFilter.getSelectionModel()
                .select(0);
        kitchenReportFilter.getSelectionModel()
                .select(0);

        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        mealCalendarKitchenFilterInjection();
        mealCalendarTableViewInitializer();
        setupMealCalendar();
    }

    // Start------------ Student > Add Student -------------

    @FXML
    private void addStudentSubmit() {
//        try {
//            new StudentDAO().save(student);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            System.out.println(addStudentSectionFields());
            showAlert(true);
        } catch (Exception e) {
            showAlert(false);
        }
    }

    private Student addStudentSectionFields() {
        return new Student(
                (long) getFieldValue(rollIdField, FieldController.LONG),
                (long) getFieldValue(nationalIdField, FieldController.LONG),
                (String) getFieldValue(firstNameField,
                        FieldController.STRING),
                (String) getFieldValue(lastNameField,
                        FieldController.STRING),
                (byte) radioButtonValue(new RadioButton[]{male, female}),
                (byte) getFieldValue(mealLimitField,
                        FieldController.BYTE)
        );
    }

    // End--------------- Student > Add Student -------------

    // Start------------ Student > Modify Student -------------

    @FXML
    private void studentModifyTableViewItemListener() {
        Object object =
                studentModifyTableView.getSelectionModel()
                        .getSelectedItem();
        if (object != null) {
            Student student =
                    (Student) object;
            SharedPreferences.add("selectedStudentModifySection", student);
            enableModificationSection();
        }
    }

    private void enableModificationSection() {
        Student student =
                (Student) SharedPreferences.get("selectedStudentModifySection");
        selectedStudentRollId
                .setText(String.valueOf(student.getRollId()));
        setChangeTab(student);
        studentModificationSaveButton.setDisable(false);
        studentModificationFilter.setDisable(false);
    }

    private void setChangeTab(Student selected) {
        changeFirstName.setText(String.valueOf(selected.getFirstName()));
        changeLastName.setText(String.valueOf(selected.getLastName()));
        changeMealLimit.setText(String.valueOf(selected.getMealLimit()));
        if (selected.getGender() == 0) changeMale.setSelected(true);
        else changeFemale.setSelected(true);
    }

    @FXML
    private void studentModificationSearchButtonListener() {
        long id;
        try {
            id = (long) getFieldValue(studentModifySearchField,
                    FieldController.LONG);
        } catch (Exception e) {
            id = (long) -1;
        }
        int selectionFilter = 0;
        if (modifyFilterSelection.isSelected(1))
            selectionFilter = 1;
        if (selectionFilter == 0) {
            studentModifyTableView.getItems().clear();

            List<Student> students = new ArrayList<>();
            try {
                students =
                        new StudentDAO().rollIdGet(id);
                studentModifyTableView.getItems().addAll(students);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            studentModifyTableView.getItems().clear();

            List<Student> students = new ArrayList<>();
            try {
                students =
                        new StudentDAO().IdGet(id);
                studentModifyTableView.getItems().addAll(students);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void updateStudent() {
//        try {
//            new StudentDAO().updateNameSex(student);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            System.out.println(updateStudentModifySectionFields());
        } catch (NullPointerException e) {
            System.out.println("The information is exactly the " +
                    "same nothing to change");
        }
    }

    private Student updateStudentModifySectionFields() {
        Student student = (Student) SharedPreferences
                .get("selectedStudentModifySection");
        Student newStudent = new Student(
                student.getRollId(),
                student.getId(),
                (String) getFieldValue(changeFirstName,
                        FieldController.STRING),
                (String) getFieldValue(changeLastName,
                        FieldController.STRING),
                (byte) radioButtonValue(new RadioButton[]{changeMale,
                        changeFemale}),
                (byte) getFieldValue(changeMealLimit,
                        FieldController.BYTE)
        );
        if (student.differs(newStudent)) return newStudent;
        else throw new NullPointerException();
    }

    private void modifyStudentFilterInjection() {
        addComboItems(studentModifySearchFilterCombo,
                "Roll id", "National id");
        studentModifySearchFilterCombo
                .getSelectionModel().select(0);
        addComboItems(studentModificationFilter,
                "Personal information", "Meal information",
                "Kitchen information");
        studentModificationFilter
                .getSelectionModel().select(0);
    }

    private void studentModifyTableViewInitializer() {
        TableColumn<Student, String> nameColumn =
                new TableColumn<>("First Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn<Student, String> lastNameColumn =
                new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn<Student, Long> rollIdColumn =
                new TableColumn<>("Roll id");
        rollIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("rollId"));

        TableColumn<Student, Long> nationalIdColumn =
                new TableColumn<>("National id");
        nationalIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        studentModifyTableView.getColumns().addAll(
                nameColumn, lastNameColumn, rollIdColumn,
                nationalIdColumn
        );
    }

    @FXML
    private void setModificationSection() {
        studentModificationTabPane.getSelectionModel()
                .select(studentModificationFilter
                        .getSelectionModel().getSelectedIndex());
    }

    // End-------------- Student > Modify Student -------------

    // Start------------ Meal > Add meal -------------

    @FXML
    private void addMealSubmit() {
        System.out.println(addMealSectionFields());
    }

    private Meal addMealSectionFields() {
        return new Meal(
                0,
                (byte) typeFilter.getSelectionModel()
                        .getSelectedIndex(),
                (String) getFieldValue(nameField,
                        FieldController.STRING),
                (double) getFieldValue(priceField,
                        FieldController.DOUBLE)
        );
    }

    private void mealFilterInjection() {
        addComboItems(typeFilter,
                "Breakfast", "Launch", "Dinner");

        addComboItems(changeMealTypeFilter,
                "Breakfast", "Launch", "Dinner");
    }

    // End-------------- Meal > Add meal -------------

    // Start------------ Meal > Modify meal -------------

    @FXML
    private void searchMeal() {
        mealModifyTableView
                .getItems().clear();
        String name = "";
        try {
            name =
                    (String) getFieldValue(mealModifySearchField,
                            FieldController.STRING);
        } catch (Exception e) {

        }
        if (name != null && !name.isEmpty()) {

            List<Meal> meals;

            try {
                meals = new MealDAO().nameGet(name);
                mealModifyTableView.getItems().addAll(meals);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void mealModifyTableViewItemListener() {
        Object object =
                mealModifyTableView.getSelectionModel()
                        .getSelectedItem();
        if (object != null) {
            Meal meal = (Meal) object;
            SharedPreferences.add("mealModification", meal);
            enableMealModification();
        }
    }

    @FXML
    private void updateMeal() {
        System.out.println(changeMealFields());
    }

    private Meal changeMealFields() {
        Meal meal =
                (Meal) SharedPreferences
                        .get("mealModification");
        return new Meal(
                meal.getId(),
                (byte) changeMealTypeFilter.getSelectionModel()
                        .getSelectedIndex(),
                (String) getFieldValue(changeMealName,
                        FieldController.STRING),
                (double) getFieldValue(changeMealPrice,
                        FieldController.DOUBLE)
        );
    }

    private void mealModifyTableViewInitializer() {
        TableColumn<Meal, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<Meal, Double> priceColumn =
                new TableColumn<>("Price");
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        TableColumn<Meal, Byte> typeColumn =
                new TableColumn<>("Type");
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type"));

        mealModifyTableView.getColumns().addAll(
                nameColumn, priceColumn, typeColumn
        );
    }

    private void enableMealModification() {
        Meal meal =
                (Meal) SharedPreferences.get("mealModification");
        changeMealName.setText(meal.getName());
        changeMealPrice.setText(String.valueOf(meal.getPrice()));
        changeMealTypeFilter.getSelectionModel()
                .select(meal.getType());
    }

    // End-------------- Meal > Modify meal -------------

    // Start------------ Meal > Meal Calendar -------------

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
        });
        exec.execute(task);
    }

    @FXML
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
            System.out.println(taskCalendar);
            preButton.setDisable(false);
            nextButton.setDisable(false);
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

    // End-------------- Meal > Meal Calendar -------------

    // Start------------ Kitchen > Add Kitchen -------------

    @FXML
    private void addKitchenSubmit() {
        System.out.println(addKitchenSectionFields());
    }

    private Kitchen addKitchenSectionFields() {
        return new Kitchen(
                0,
                (String) getFieldValue(kitchenName,
                        FieldController.STRING),
                (byte) kitchenType.getSelectionModel()
                        .getSelectedIndex()
        );
    }

    private void kitchenTypeFilterInjection() {
        addComboItems(kitchenType,
                "Men", "Women", "Men-Women");
        addComboItems(changeKitchenType,
                "Men", "Women", "Men-Women");
    }

    // End-------------- Kitchen > Add Kitchen -------------

    // Start------------ Kitchen > Modify Kitchen -------------

    @FXML
    private void updateKitchen() {
        System.out.println(changeKitchenFields());
    }

    private Kitchen changeKitchenFields() {
        Kitchen kitchen =
                (Kitchen) SharedPreferences
                        .get("modifyKitchen");
        return new Kitchen(
                kitchen.getId(),
                (String) getFieldValue(changeKitchenName,
                        FieldController.STRING),
                (byte) changeKitchenType.getSelectionModel()
                        .getSelectedIndex()
        );
    }

    @FXML
    private void searchKitchen() {
        String name = "";
        try {
            name =
                    (String) getFieldValue(kitchenModifySearchField,
                            FieldController.STRING);
        } catch (Exception e) {

        }
        if (name != null && !name.isEmpty()) {

            List<Kitchen> kitchens;

            try {
                kitchenModifyTableView.getItems().clear();
                kitchens = new KitchenDAO().nameGet(name);
                kitchenModifyTableView.getItems().addAll(kitchens);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void kitchenModifyTableViewItemListener() {
        kitchenModifyTableView.setOnMouseClicked(event -> {
            Object object =
                    kitchenModifyTableView.getSelectionModel()
                            .getSelectedItem();
            if (object != null) {
                Kitchen kitchen =
                        (Kitchen) object;
                SharedPreferences.add("modifyKitchen", kitchen);
                enableKitchenModification();
            }
        });
    }

    private void enableKitchenModification() {
        Kitchen kitchen =
                (Kitchen) SharedPreferences.get("modifyKitchen");
        changeKitchenName.setText(kitchen.getName());
        changeKitchenType.getSelectionModel()
                .select(kitchen.getKitchenType());
    }

    private void kitchenModifyTableViewInitializer() {
        TableColumn<Kitchen, String> nameColumn =
                new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        nameColumn.setReorderable(false);
        nameColumn.setSortable(false);

        TableColumn<Kitchen, Byte> kitchenTypeColumn =
                new TableColumn<>("Type");
        kitchenTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("kitchenType"));
        kitchenTypeColumn.setReorderable(false);
        kitchenTypeColumn.setSortable(false);

        kitchenModifyTableView.getColumns().addAll(
                nameColumn, kitchenTypeColumn
        );
    }

    // End-------------- Kitchen > Modify Kitchen -------------

    // Start------------ Report > Student -------------

    @FXML
    private void getStudentReport() {
        studentReportTableView.getItems()
                .clear();
        int studentFilter = studentReportFilter
                .getSelectionModel().getSelectedIndex();
        if (studentFilter == 2) studentFilter = -1;
        try {
            int finalStudentFilter = studentFilter;
            Task<List<Student>> task = new Task<>() {
                @Override
                public List<Student> call() throws Exception {
                    return new StudentDAO().getAll(finalStudentFilter);
                }
            };

            task.setOnFailed(e -> task.getException().printStackTrace());
            task.setOnSucceeded(e -> {
                List<Student> taskStudent = task.getValue();
                studentReportTableView
                        .getItems().addAll(taskStudent);
                studentReportTotal
                        .setText(String.valueOf(taskStudent.size()));
            });
            exec.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void studentReportTableViewInitializer() {
        TableColumn<Student, String> nameColumn =
                new TableColumn<>("First Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn<Student, String> lastNameColumn =
                new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn<Student, Long> rollIdColumn =
                new TableColumn<>("Roll id");
        rollIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("rollId"));

        TableColumn<Student, Long> nationalIdColumn =
                new TableColumn<>("National id");
        nationalIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Student, Byte> mealLimitColumn =
                new TableColumn<>("Meal limit");
        mealLimitColumn.setCellValueFactory(
                new PropertyValueFactory<>("mealLimit"));

        studentReportTableView.getColumns().addAll(
                nameColumn, lastNameColumn, rollIdColumn,
                nationalIdColumn, mealLimitColumn
        );
    }

    private void reportFilterInjection() {
        addComboItems(studentReportFilter,
                "Male", "Female", "Male-Female");
        addComboItems(mealReportFilter,
                "Breakfast", "Launch", "Dinner", "All");
        addComboItems(kitchenReportFilter,
                "Men", "Women", "Men-Women");
    }

    // End-------------- Report > Student -------------

    // Start------------ Report > Meal -------------

    @FXML
    private void getMealReport() {
        mealReportTableView.getItems()
                .clear();
        int filter = mealReportFilter
                .getSelectionModel().getSelectedIndex();
        if (filter == 3) filter = -1;
        try {
            int finalFilter = filter;
            Task<List<Meal>> task = new Task<>() {
                @Override
                public List<Meal> call() throws Exception {
                    return new MealDAO().typeGet(finalFilter);
                }
            };

            task.setOnFailed(e -> task.getException().printStackTrace());
            task.setOnSucceeded(e -> {
                List<Meal> taskMeal = task.getValue();
                mealReportTotal
                        .setText(String.valueOf(taskMeal.size()));
                mealReportTableView.getItems()
                        .addAll(taskMeal);
            });
            exec.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mealReportTableViewInitializer() {
        TableColumn<Meal, String> nameColumn =
                new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<Meal, Double> priceColumn =
                new TableColumn<>("Price");
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        mealReportTableView.getColumns().addAll(
                nameColumn, priceColumn
        );
    }

    // End-------------- Report > Meal -------------

    // Start------------ Report > Kitchen -------------

    @FXML
    private void getKitchenReport() {
        kitchenReportTableView.getItems()
                .clear();
        int filter = kitchenReportFilter
                .getSelectionModel().getSelectedIndex();
        if (filter == 2) filter = -1;
        try {
            int finalFilter = filter;
            Task<List<Kitchen>> task = new Task<>() {
                @Override
                public List<Kitchen> call() throws Exception {
                    return new KitchenDAO().typeGet(finalFilter);
                }
            };

            task.setOnFailed(e -> task.getException().printStackTrace());
            task.setOnSucceeded(e -> {
                List<Kitchen> taskKitchen = task.getValue();
                kitchenReportTotal
                        .setText(String.valueOf(taskKitchen.size()));
                kitchenReportTableView.getItems()
                        .addAll(taskKitchen);
            });
            exec.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void kitchenReportTableViewInitializer() {
        TableColumn<Kitchen, String> nameColumn =
                new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        kitchenReportTableView.getColumns().addAll(
                nameColumn
        );
    }

    // End-------------- Report > Kitchen -------------

    private void showAlert(boolean isGood) {
        if (!isGood) {
            errorBadge.setVisible(true);
            successBadge.setVisible(false);
            System.out.println("error badge showed be shown");
        } else {
            errorBadge.setVisible(false);
            successBadge.setVisible(true);
        }
    }

    private void showErrorDialog() {
//        Alert alert = new Alert(Alert.AlertType.NONE,
//                "Any kinda space in numeric values are forbidden and" +
//                        "unnecessary white spaces will be deleted from input," +
//                        " keep also in mind that there shouldn't be any numeric" +
//                        "values in non numerical fields.",
//                ButtonType.OK);
//        alert.showAndWait();
    }

    private void reportSectionClickListeners() {
        studentReportTab.setOnMouseClicked(event ->
                reportTabSelection.select(0));

        mealReportTab.setOnMouseClicked(event ->
                reportTabSelection.select(1));

        kitchenReportTab.setOnMouseClicked(event ->
                reportTabSelection.select(2));

        transactionReportTab.setOnMouseClicked(event ->
                reportTabSelection.select(3));
    }

    private void kitchenSectionClickListeners() {
        addKitchenTab.setOnMouseClicked(event ->
                kitchenTabSelection.select(0));

        modifyKitchenTab.setOnMouseClicked(event ->
                kitchenTabSelection.select(1));
    }

    private void mealSectionClickListeners() {
        addMealTab.setOnMouseClicked(event ->
                mealTabSelection.select(0));

        modifyMealTab.setOnMouseClicked(event ->
                mealTabSelection.select(1));

        mealCalendarTab.setOnMouseClicked(event ->
                mealTabSelection.select(2));
    }

    private void sideMenuClickListeners() {
        studentMenuSection.setOnMouseClicked(event -> {
            mainTabSelection.select(0);
            studentTabSelection.select(0);
            sectionTitle.setText("Student");
            changeBackgroundColor(0);
            studentMenuSection.setStyle("-fx-background-color: white;" +
                    "-fx-text-fill: #0f4c75");
            mainSelectedTab = 0;
        });

        mealMenuSection.setOnMouseClicked(event -> {
            mainTabSelection.select(1);
            mealTabSelection.select(0);
            sectionTitle.setText("Meal");
            changeBackgroundColor(1);
            mealMenuSection.setStyle("-fx-background-color: white;" +
                    "-fx-text-fill: #0f4c75");
            mainSelectedTab = 1;
        });

        kitchenMenuSection.setOnMouseClicked(event -> {
            mainTabSelection.select(2);
            kitchenTabSelection.select(0);
            sectionTitle.setText("Kitchen");
            changeBackgroundColor(2);
            kitchenMenuSection.setStyle("-fx-background-color: white;" +
                    "-fx-text-fill: #0f4c75");
            mainSelectedTab = 2;
        });

        reportMenuSection.setOnMouseClicked(event -> {
            mainTabSelection.select(3);
            reportTabSelection.select(0);
            sectionTitle.setText("Report");
            changeBackgroundColor(3);
            reportMenuSection.setStyle("-fx-background-color: white;" +
                    "-fx-text-fill: #0f4c75");
            mainSelectedTab = 3;
        });
    }

    private void changeBackgroundColor(int mainSelectedTab) {
        if (mainSelectedTab != this.mainSelectedTab) {
            if (this.mainSelectedTab == 0)
                studentMenuSection.setStyle("-fx-background-color: transparent");
            else if (this.mainSelectedTab == 1)
                mealMenuSection.setStyle("-fx-background-color: transparent");
            else if (this.mainSelectedTab == 2)
                kitchenMenuSection.setStyle("-fx-background-color: transparent");
            else reportMenuSection.setStyle("-fx-background-color: transparent");
        }
    }

    private void studentClickListeners() {
        addStudentTab.setOnMouseClicked(event -> {
            studentTabSelection.select(0);
        });

        modifyStudentTab.setOnMouseClicked(event -> {
            studentTabSelection.select(1);
        });
    }

    private void setDayAndDate() {
        PersianDate today = PersianDate.now();
        String dayAndDate = today.getDayOfWeek() + " " +
                today.toString().replaceAll("-", "/");
        date.setText(dayAndDate);
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
        }
        return null;
    }

    private int radioButtonValue(RadioButton... radioButtons) {
        for (RadioButton rb : radioButtons)
            if (rb.isSelected()) return getId(rb);
        return -1;
    }

    private int getId(RadioButton radioButton) {
        if (radioButton.getText().equals(Student.S_MALE))
            return Student.MALE;
        else if (radioButton.getText().equals(Student.S_FEMALE))
            return Student.FEMALE;
        return -1;
    }

    private void addComboItems(JFXComboBox comboBox,
                               String... items) {
        comboBox.getItems().addAll(items);
    }
}