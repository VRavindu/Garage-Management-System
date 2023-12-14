package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.SupplierDto;
import lk.ijse.Model.SupplierModel;

import java.sql.SQLException;

public class DeleteSupplierFormController {
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new SupplierModel();

        try {
            SupplierDto dto = model.searchSupplier(id);

            if (dto != null){
                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(SupplierDto dto) {
        txtId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtTel.setText(String.valueOf(dto.getTel()));
        txtEmail.setText(dto.getEmail());
        txtDate.setText(dto.getDate());
        txtEmpId.setText(dto.getE_id());
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new SupplierModel();

        boolean isDeleted = false;
        try {
            isDeleted = model.deleteSupplier(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
        txtEmail.setText("");
        txtEmpId.setText("");
        txtDate.setText("");
    }
}
