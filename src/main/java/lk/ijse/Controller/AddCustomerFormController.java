package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.Dto.CustomerDto;
import lk.ijse.Dto.EmployeeDto;
import lk.ijse.Model.CustomerModel;
import lk.ijse.Model.EmployeeModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class AddCustomerFormController {
    @FXML
    private TextField txtAddress;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private JFXComboBox<String> cmbEmpId;
    private final EmployeeModel employeeModel = new EmployeeModel();

    public void initialize(){
        loadEmployeeIds();
    }
    private void loadEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> empList = employeeModel.loadAllEmployee();

            for (EmployeeDto dto : empList){
                obList.add(dto.getId());
            }
            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        boolean isValidCust = validateCustomer();
        if (isValidCust) {

            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String tel = txtTel.getText();
            String empId = cmbEmpId.getValue();
            String date = String.valueOf(txtDate.getValue());

            var custDto = new CustomerDto(id, name, address, tel, empId, date);
            var custModel = new CustomerModel();

            try {
                boolean isSaved = custModel.saveCustomer(custDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Added").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean validateCustomer() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("(C)[0-9]{3,}", id);
        if (!idMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID").show();
            return false;
        }
        String name = txtName.getText();
        boolean nameMatch = Pattern.matches("[A-Za-z\\s]{3,}", name);
        if (!nameMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Name").show();
            return false;
        }
        String address = txtAddress.getText();
        boolean addressMatch = Pattern.matches("[A-Za-z, ]{2,}", address);
        if (!addressMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Address").show();
            return false;
        }
        String tel = txtTel.getText();
        boolean telMatch = Pattern.matches("\\d{10}", tel);
        if (!telMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Telephone Number").show();
            return false;
        }
        String date = String.valueOf(txtDate.getValue());
        boolean dateMatch = Pattern.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}", date);
        if (!dateMatch) {
            new Alert(Alert.AlertType.ERROR, "Invalid Date").show();
            return false;
        }
        return true;
    }

}
