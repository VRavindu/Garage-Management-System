package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.CustomerDto;
import lk.ijse.Model.CustomerModel;

import java.sql.SQLException;

public class DeleteCustomerFormController {
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new CustomerModel();

        boolean isDeleted = false;
        try {
            isDeleted = model.deleteCustomer(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new CustomerModel();

        try {
            CustomerDto dto = model.searchCustomer(id);

            if (dto != null){
                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer not Found !").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void fillFields(CustomerDto dto) {
        txtId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtTel.setText(String.valueOf(dto.getTel()));
        txtDate.setText(dto.getDate());
        txtEmpId.setText(dto.getE_id());
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
        txtDate.setText("");
        txtEmpId.setText("");
    }
}
