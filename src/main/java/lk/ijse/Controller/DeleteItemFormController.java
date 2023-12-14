package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.ItemDto;
import lk.ijse.Model.ItemModel;

import java.sql.SQLException;

public class DeleteItemFormController {

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtPrice;

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new ItemModel();

        boolean isDeleted = false;
        try {
            isDeleted = model.deleteItem(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new ItemModel();

        try {
            ItemDto dto = model.searchItem(id);

            if (dto != null){
                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Item not Found !").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(ItemDto dto) {
        txtId.setText(dto.getItem_code());
        txtQty.setText(dto.getQty());
        txtPrice.setText(dto.getPrice());
        txtDesc.setText(dto.getDesc());
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtQty.setText("");
        txtPrice.setText("");
        txtDesc.setText("");

    }
}
