package sample;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

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
    private SelectionModel<Tab> mainTabSelection;
    private SelectionModel<Tab> walletTabSelection;
    private SelectionModel<Tab> accountTabSelection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainTabSelection = mainTabPane.getSelectionModel();
        walletTabSelection = walletTabPane.getSelectionModel();
        accountTabSelection = accountTabPane.getSelectionModel();
    }


    @FXML
    private void onWalletInfo() {
        walletTabSelection.select(0);
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
        walletTabSelection.select(0);
    }

    @FXML
    private void onAccountSection() {
        mainTabSelection.select(2);
        accountTabSelection.select(0);
    }
}
