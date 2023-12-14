package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.Dto.EmployeeDto;
import lk.ijse.Dto.RepairDto;
import lk.ijse.Dto.VehicleDto;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.RepairModel;
import lk.ijse.Model.VehicleModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class AddRepairFormController {
    @FXML
    private JFXComboBox<String> cmbEmpId;

    @FXML
    private JFXComboBox<String> cmbPlateNo;

    @FXML
    private TextField txtCost;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtRId;

    @FXML
    private TextField txtStatus;

    private final EmployeeModel employeeModel = new EmployeeModel();
    private final VehicleModel vehicleModel = new VehicleModel();

    public void initialize(){
        loadEmployeeIds();
        loadPlateNo();
    }

    private void loadPlateNo() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<VehicleDto> vehiList = vehicleModel.loadAllVehicles();

            for (VehicleDto dto : vehiList){
                obList.add(dto.getPlateNo());
            }
            cmbPlateNo.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void btnAddOnAction(ActionEvent actionEvent) {

        boolean isValidRepair = validateRepair();
        if (isValidRepair) {
            String rId = txtRId.getText();
            String status = txtStatus.getText();
            String cost = txtCost.getText();
            String empId = cmbEmpId.getValue();
            String plateNo = cmbPlateNo.getValue();
            String date = String.valueOf(txtDate.getValue());

            var repairDto = new RepairDto(rId, status, cost, plateNo, empId, date);
            var repairModel = new RepairModel();

            try {
                boolean isSaved = repairModel.saveRepair(repairDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Repair Added").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean validateRepair() {
        String id = txtRId.getText();
        boolean idMatch = Pattern.matches("(R)[0-9]{3,}", id);
        if (!idMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Repair ID").show();
            return false;
        }
        String status = txtStatus.getText();
        boolean statusMatch = Pattern.matches("[A-Za-z ]{3,}", status);
        if (!statusMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Status").show();
            return false;
        }
        String cost = txtCost.getText();
        boolean costMatch = Pattern.matches("[0-9]{3,}", cost);
        if (!costMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Cost").show();
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

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtRId.setText("");
        txtCost.setText("");
        txtStatus.setText("");
    }
}
