package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.ToolDto;
import lk.ijse.Model.ToolModel;

import java.sql.SQLException;

public class DeleteToolsFormController {
    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtStatus;

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new ToolModel();

        boolean isDeleted = false;
        try {
            isDeleted = model.deleteTool(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Tool Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new ToolModel();

        try {
            ToolDto dto = model.searchTool(id);

            if (dto != null){
                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Tool not Found !").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(ToolDto dto) {
        txtId.setText(dto.getT_code());
        txtDesc.setText(dto.getDesc());
        txtStatus.setText(dto.getStatus());
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtDesc.setText("");
        txtStatus.setText("");

    }
}
