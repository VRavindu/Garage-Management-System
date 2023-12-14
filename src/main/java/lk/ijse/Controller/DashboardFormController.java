package lk.ijse.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.ItemDto;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.ItemModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class DashboardFormController {
    @FXML
    private AnchorPane P1;

    @FXML
    private LineChart<?, ?> chartEmpDetails;

    @FXML
    private BarChart<?, ?> chrtStock;

    @FXML
    private NumberAxis daysAxis;

    @FXML
    private CategoryAxis descAxis;

    @FXML
    private CategoryAxis empAxis;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDay;

    @FXML
    private Label lblTime;

    @FXML
    private NumberAxis qtyAxis;

    private final ItemModel itemModel = new ItemModel();
    private final EmployeeModel employeeModel = new EmployeeModel();

    public void initialize(){
        barChartView();
        lineChartView();
        setDate();
        loadTime();
    }

    private void loadTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            lblTime.setText(dateFormat.format(new Date()));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }
    private void lineChartView() {
        try {
            XYChart.Series series = employeeModel.getWorkingDaysChart();
            chartEmpDetails.getData().addAll(series);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("d MMM YYYY");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("E");
        String year = date.format(f1);
        String day = date.format(f2);

        lblDate.setText(year);
        lblDay.setText(day);

    }

    private void barChartView() {

        try {
            XYChart.Series series = itemModel.getItemChart();
            chrtStock.getData().addAll(series);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.centerOnScreen();
    }

    public void btnProfileOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =  new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/View/ProfileForm.fxml"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public void btnEmpOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/EmployeeDetailsForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Employee Details");
        stage.centerOnScreen();

    }

    public void btnStockOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/ItemStockManageForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Item Stock Manage");
        stage.centerOnScreen();
    }

    public void btnCustOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/CustomerDetailsForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer Details");
        stage.centerOnScreen();
    }

    public void btnSupOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/SupplierDetailsForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) P1.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Supplier Details");
        stage.centerOnScreen();
    }

}
