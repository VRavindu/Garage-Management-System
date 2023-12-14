package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.ToolDto;
import lk.ijse.Model.ToolModel;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddToolsFormController {
    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtStatus;

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean isValidTool = validateTool();
        if (isValidTool) {
            String id = txtId.getText();
            String desc = txtDesc.getText();
            String status = txtStatus.getText();

            var dto = new ToolDto(id, desc, status);
            var model = new ToolModel();

            try {
                boolean isSaved = model.saveTool(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Tool Added Successfully").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFileds();
    }

    private void clearFileds() {
        txtId.setText("");
        txtDesc.setText("");
        txtStatus.setText("");
    }

    private boolean validateTool() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("(T)[0-9]{3,}", id);
        if (!idMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Repair ID").show();
            return false;
        }
        String desc = txtDesc.getText();
        boolean descMatch = Pattern.matches("[A-Za-z ]{3,}", desc);
        if (!descMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Description").show();
            return false;
        }
        String status = txtStatus.getText();
        boolean statusMatch = Pattern.matches("[A-Za-z ]{3,}", status);
        if (!statusMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Status").show();
            return false;
        }
        return true;
    }
}
