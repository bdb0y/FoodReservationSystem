package sample;

import com.dev.foodreservation.database.MealDAO;
import com.dev.foodreservation.database.StudentDAO;
import com.dev.foodreservation.database.utilities.FieldController;
import com.dev.foodreservation.database.utilities.SharedPreferences;
import com.dev.foodreservation.objects.Kitchen;
import com.dev.foodreservation.objects.Meal;
import com.dev.foodreservation.objects.Student;
import com.github.mfathi91.time.PersianDate;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;


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
    JFXButton mealReportTab;
    @FXML
    JFXButton kitchenReportTab;
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
        addStudentSubmitClickListener();
        modifyStudentFilterInjection();
        modifyStudentClickListener();
        studentModifyTableViewInitializer();
        studentModifyTableViewItemListener();
        studentModificationSaveButtonListener();
        addMealSubmitButtonClickListener();
        mealFilterInjection();
        mealModifySearchClickListener();
        mealModifyTableViewInitializer();
        mealModifyTableViewItemListener();
        kitchenSubmitButtonClickListener();

        studentClickListeners();
    }

    private void kitchenSubmitButtonClickListener() {
        kitchenSubmitButton.setOnMouseClicked(event ->
                saveKitchen());
    }

    private void saveKitchen() {
        System.out.println(addKitchenSectionFields());
    }

    private Kitchen addKitchenSectionFields() {
        return new Kitchen(
                0,
                (String) getFieldValue(kitchenName, FieldController.STRING),
                (byte) kitchenType.getSelectionModel().getSelectedIndex()
        );
    }

    private void mealModifySearchClickListener() {
        mealModifySearchButton.setOnMouseClicked(event ->
                searchMeal());
    }

    private void searchMeal() {
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

    private void addMealSubmitButtonClickListener() {
        addMealSubmitButton.setOnMouseClicked(event ->
                saveMeal());
    }

    private void saveMeal() {
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

    private void studentModificationSaveButtonListener() {
        studentModificationSaveButton.setOnMouseClicked(event -> {
            updateStudent();
        });
    }

    private void updateStudent() {
//        byte gender = 0;
//        if (!changeMale.isSelected()) gender = 1;
//        Student student = new Student(
//                Long.parseLong(selectedStudentRollId.getText()),
//                0,
//                changeFirstName.getText(),
//                changeLastName.getText(),
//                gender,
//                Byte.parseByte(changeMealLimit.getText())
//        );
//        try {
//            new StudentDAO().updateNameSex(student);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        System.out.println(updateStudentModifySectionFields());

    }

    private void studentModifyTableViewItemListener() {
        studentModifyTableView.setOnMouseClicked(event -> {
            Object object =
                    studentModifyTableView.getSelectionModel()
                            .getSelectedItem();
            if (object != null) {
                Student student =
                        (Student) object;
                SharedPreferences.add("selectedStudentModifySection", student);
                enableModificationSection();
            }
        });

    }

    private void mealModifyTableViewItemListener() {
        mealModifyTableView.setOnMouseClicked(event -> {
            Object object =
                    mealModifyTableView.getSelectionModel().getSelectedItem();
            if (object != null) {
                Meal meal = (Meal) object;
                SharedPreferences.add("mealModification", meal);
                enableMealModification();
            }
        });
    }

    private void enableMealModification() {
        Meal meal =
                (Meal) SharedPreferences.get("mealModification");
        changeMealName.setText(meal.getName());
        changeMealPrice.setText(String.valueOf(meal.getPrice()));
        changeMealTypeFilter.getSelectionModel()
                .select(meal.getType());
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

    private void studentModifyTableViewInitializer() {
        TableColumn nameColumn = new TableColumn("First Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn rollIdColumn = new TableColumn("Roll id");
        rollIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("rollId"));

        TableColumn nationalIdColumn = new TableColumn("National id");
        nationalIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        studentModifyTableView.getColumns().addAll(
                nameColumn, lastNameColumn, rollIdColumn,
                nationalIdColumn
        );
    }

    private void mealModifyTableViewInitializer() {
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type"));

        mealModifyTableView.getColumns().addAll(
                nameColumn, priceColumn, typeColumn
        );
    }

    private void modifyStudentClickListener() {
        studentModifySearchButton
                .setOnMouseClicked(event ->
                        studentModificationSearchButtonListener());
    }

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

    private void modifyStudentFilterInjection() {
        List<String> modifyFilter = new ArrayList<>();
        modifyFilter.add("Roll id");
        modifyFilter.add("National id");
        studentModifySearchFilterCombo.getItems().addAll(modifyFilter);
        modifyFilterSelection.select(0);

        List<String> modificationFilter = new ArrayList<>();
        modificationFilter.add("Personal information");
        modificationFilter.add("Meal information");
        modificationFilter.add("Kitchen information");
        studentModificationFilter.getItems().addAll(modificationFilter);
        studentModificationFilter.setOnAction(event ->
                studentModificationFilterAction());
        modificationSelection.select(0);
    }

    private void mealFilterInjection() {
        List<String> typeFilterItems = new ArrayList<>();
        typeFilterItems.add("Breakfast");
        typeFilterItems.add("Launch");
        typeFilterItems.add("Dinner");
        typeFilter.getItems().addAll(typeFilterItems);

        List<String> changeTypeFilter = new ArrayList<>();
        changeTypeFilter.add("Breakfast");
        changeTypeFilter.add("Launch");
        changeTypeFilter.add("Dinner");
        changeMealTypeFilter.getItems().addAll(changeTypeFilter);
    }

    private void studentModificationFilterAction() {

        if (modificationSelection.isSelected(0))
            modificationTabSelection.select(0);
        else if (modificationSelection.isSelected(1))
            modificationTabSelection.select(1);
        else modificationTabSelection.select(2);
    }

    private void addStudentSubmitClickListener() {
        submitButton.setOnMouseClicked(event -> addStudentSubmit());
    }

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
        PersianDate persianDate = PersianDate.now();
        String dayAndDate = persianDate.getDayOfWeek() + " " +
                persianDate.toString().replaceAll("-", "/");
        date.setText(dayAndDate);
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

    private Student updateStudentModifySectionFields() {
        Student student = (Student) SharedPreferences
                .get("selectedStudentModifySection");
        return new Student(
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
    }

    private Object getFieldValue(TextField textField,
                                 int fieldController) {
        if (textField == null) return null;
        String value = textField.getText().trim();
        if (fieldController == FieldController.STRING) {
            for (char c : value.toCharArray())
                if (!Character.isAlphabetic(c))
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
}