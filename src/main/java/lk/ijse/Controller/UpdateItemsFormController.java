package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.Dto.ItemDto;
import lk.ijse.Model.ItemModel;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateItemsFormController {

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtPrice;

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String code = txtId.getText();

        var model = new ItemModel();

        try {
            ItemDto itemDto = model.searchItem(code);

            if (itemDto != null){
                fillFields(itemDto);
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

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isValidItem = validateItem();
        if (isValidItem) {
            String code = txtId.getText();
            String qty = txtQty.getText();
            String price = txtPrice.getText();
            String desc = txtDesc.getText();

            var dto = new ItemDto(code, qty, price, desc);
            var model = new ItemModel();

            try {
                boolean isUpdate = model.updateItem(dto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Updated").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage());
            }
        }
    }

    private boolean validateItem() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("(I)[0-9]{3,}", id);
        if (!idMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID").show();
            return false;
        }
        String qty = txtQty.getText();
        boolean qtyMatch = Pattern.matches("[0-9]{1,}", qty);
        if (!qtyMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity").show();
            return false;
        }
        String price = txtPrice.getText();
        boolean priceMatch = Pattern.matches("[0-9]{1,}", price);
        if (!priceMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Price").show();
            return false;
        }
        String desc = txtDesc.getText();
        boolean descMatch = Pattern.matches("[A-Za-z]{3,}", desc);
        if (!descMatch){
            new Alert(Alert.AlertType.ERROR, "Invalid Description").show();
            return false;
        }
        return true;
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
