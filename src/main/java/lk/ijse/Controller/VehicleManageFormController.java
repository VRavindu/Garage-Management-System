package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dto.VehicleDto;
import lk.ijse.Dto.tm.VehicleTm;
import lk.ijse.Model.VehicleModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VehicleManageFormController {

    @FXML
    private AnchorPane P1;

    @FXML
    private TableColumn<?, ?> colCustId;

    @FXML
    private TableColumn<?, ?> colPltNo;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<VehicleTm> tblVehicle;

    public void initialize() {
        setCellValueFactory();
        loadAllVehicles();
    }

    private void setCellValueFactory() {
        colPltNo.setCellValueFactory(new PropertyValueFactory<>("plateNo"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
    }

    private void loadAllVehicles() {
        var model = new VehicleModel();

        ObservableList<VehicleTm> obList = FXCollections.observableArrayList();

        try {
            List<VehicleDto> dtoList = model.getAllVehicles();

            for (VehicleDto dto : dtoList){
                obList.add(
                        new VehicleTm(
                                dto.getPlateNo(),
                                dto.getType(),
                                dto.getCustId()
                        )
                );
            }
            tblVehicle.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Dashboard");
        stage.centerOnScreen();
    }

    public void btnCustDetailsOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/CustomerDetailsForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer Details");
        stage.centerOnScreen();
    }

    public void btnRepairOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/RepairManageForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Repair Manage");
        stage.centerOnScreen();
    }

    public void btnAddVehiOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =  new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/View/AddVehicleForm.fxml"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public void btnUpdateVehiOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =  new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/View/UpdateVehicleForm.fxml"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}
