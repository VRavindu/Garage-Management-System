package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.VehicleDto;
import lk.ijse.Model.VehicleModel;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateVehicleFormController {
    @FXML
    private TextField txtCustId;

    @FXML
    private TextField txtPtNo;

    @FXML
    private TextField txtVType;

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String plateNo = txtPtNo.getText();

        var model = new VehicleModel();

        try {
            VehicleDto vehicleDto = model.searchVehicle(plateNo);

            if (vehicleDto != null){
                fillFields(vehicleDto);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(VehicleDto dto) {

        txtPtNo.setText(dto.getPlateNo());
        txtVType.setText(dto.getType());
        txtCustId.setText(dto.getCustId());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isValidVehicle = validateVehicle();
        if (isValidVehicle) {
            String plateNo = txtPtNo.getText();
            String type = txtVType.getText();
            String custId = txtCustId.getText();

            var dto = new VehicleDto(plateNo, type, custId);
            var model = new VehicleModel();

            try {
                boolean isUpdate = model.updateVehicle(dto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Updated").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Vehicle not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage());
            }
        }
    }

    private boolean validateVehicle() {
        String type = txtVType.getText();
        boolean typeMatch = Pattern.matches("[A-Za-z]{3,}", type);
        if (!typeMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Type").show();
            return false;
        }
        String id = txtCustId.getText();
        boolean idMatch = Pattern.matches("(C)[0-9]{3,}", id);
        if (!idMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID").show();
            return false;
        }
        return true;
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtPtNo.setText("");
        txtVType.setText("");
        txtCustId.setText("");

    }
}
