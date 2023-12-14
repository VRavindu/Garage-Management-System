package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.EmployeeDto;
import lk.ijse.Model.EmployeeModel;

import java.sql.SQLException;

public class DeleteEmployeeFormController {
    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton delete;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtJob;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtTel;


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new EmployeeModel();

        boolean isDeleted = false;
        try {
            isDeleted = model.deleteEmployee(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new EmployeeModel();

        try {
            EmployeeDto dto = model.searchEmployee(id);

            if (dto != null){
                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee not Found !").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void fillFields(EmployeeDto dto) {
        txtId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtNic.setText(dto.getNic());
        txtAddress.setText(dto.getAddress());
        txtTel.setText(dto.getTel());
        txtEmail.setText(dto.getEmail());
        txtJob.setText(dto.getJob());
        txtDate.setText(dto.getDate());
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtNic.setText("");
        txtAddress.setText("");
        txtTel.setText("");
        txtEmail.setText("");
        txtJob.setText("");
        txtDate.setText("");
    }
}
