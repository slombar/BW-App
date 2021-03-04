package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.UserTypes.User;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class ManageEmployeesController implements Initializable {
    public StackPane popupPane;
    public JFXComboBox<String> currentEmployeesCombobox;
    public JFXButton rmEmployeeButton;

    /**
     * is run when the class is made
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBox();
    }

    /**
     * sets the combobox of employees to be all current employees in the database.
     */
    private void initComboBox() {
        ObservableList<User> userList = UserHandling.getUsers();
        ObservableList<String> employeeNames = FXCollections.observableArrayList();
        String name = "";
//TODO maybe make this use autocomplete as i dont think a combobox is the way to go with a full hospital
        for (User u : userList) {
            if (u.isEmployee() && (u.isAdmin())) {
                name = u.getUsername() + " -> " + u.getFirstName() + " " + u.getLastName() + ":  Admin";
            } else if (u.isEmployee()) {
                name = u.getUsername() + " -> " + u.getFirstName() + " " + u.getLastName() + ":  Staff";
            } else {
                name = u.getUsername() + " -> " + u.getFirstName() + " " + u.getLastName() + ":  Patient";
            }

            employeeNames.add(name);
        }
        currentEmployeesCombobox.setItems(employeeNames);
    }

    /**
     * goes back to the staffMainPage
     *
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        SwitchScene.goToParent("/Views/StaffMainPage.fxml");
    }

    /**
     * goes to add employee form
     *
     * @param actionEvent
     */
    public void addEmployee(ActionEvent actionEvent) {
        SwitchScene.goToParent("/Views/CreateEmployeeAccount.fxml");
    }

    /**
     * removes the employee selected in the combobox from the database
     *
     * @param actionEvent
     */
    public void rmEmployee(ActionEvent actionEvent) {
        String emp = currentEmployeesCombobox.getValue();

        // splits at first space to get username
        int i = emp.indexOf(' ');
        String username = emp.substring(0, i);

        // System.out.println("deleting " + username);
        UserHandling.deleteUser(username);

        SwitchScene.goToParent("/Views/ManageEmployees.fxml");
    }

    /**
     * sets the button to remove employees to visible only if one is selected
     *
     * @param actionEvent
     */
    public void employeeSelected(ActionEvent actionEvent) { //TODO probably never turns invisible again
        rmEmployeeButton.setVisible(true);
    }
}
