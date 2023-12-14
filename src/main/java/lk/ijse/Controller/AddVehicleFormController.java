package lk.ijse.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.Dto.CustomerDto;
import lk.ijse.Dto.VehicleDto;
import lk.ijse.Model.CustomerModel;
import lk.ijse.Model.VehicleModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class AddVehicleFormController {

    @FXML
    private ComboBox<String> cmbCustId;

    @FXML
    private TextField txtPtNo;

    @FXML
    private TextField txtVType;

    private final CustomerModel customerModel = new CustomerModel();

    public void initialize() {
        loadCustomerIds();
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> custList = customerModel.loadAllCustomers();

            for (CustomerDto dto : custList) {
                obList.add(dto.getId());
            }
            cmbCustId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean isValidVehicle = validateVehicle();
        if (isValidVehicle) {
            String id = txtPtNo.getText();
            String desc = txtVType.getText();
            String custId = cmbCustId.getValue();

            var dto = new VehicleDto(id, desc, custId);
            var model = new VehicleModel();

            try {
                boolean isSaved = model.saveVehicle(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Added Successfully").show();
                }
            } catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFileds();
    }

    private void clearFileds() {
        txtPtNo.setText("");
        txtVType.setText("");

    }

    private boolean validateVehicle() {
        String plate = txtPtNo.getText();
        boolean plateMatch = Pattern.matches("[A-Za-z0-9 -]{4,}", plate);
        if (!plateMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Plate Number").show();
            return false;
        }
        String type = txtVType.getText();
        boolean typeMatch = Pattern.matches("[A-Za-z]{3,}", type);
        if (!typeMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Type").show();
            return false;
        }
        return true;
    }
}
