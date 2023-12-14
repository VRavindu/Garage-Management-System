package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.EmployeeDto;
import lk.ijse.Dto.ToolDto;
import lk.ijse.Dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleModel {
    public boolean saveVehicle(VehicleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO vehicle VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPlateNo());
        pstm.setString(2, dto.getType());
        pstm.setString(3, dto.getCustId());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }

    public boolean updateVehicle(VehicleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE vehicle SET type = ?, cust_id = ? WHERE plate_no = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getType());
        pstm.setString(2, dto.getCustId());
        pstm.setString(3, dto.getPlateNo());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }

    public VehicleDto searchVehicle(String plateNo) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM vehicle WHERE plate_no = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, plateNo);

        ResultSet resultSet = pstm.executeQuery();

        VehicleDto dto = null;

        if (resultSet.next()){
            String plate_no = resultSet.getString(1);
            String Vtype = resultSet.getString(2);
            String custId = resultSet.getString(3);

            dto = new VehicleDto(plate_no, Vtype, custId);
        }
        return dto;
    }

    public List<VehicleDto> getAllVehicles() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM vehicle";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<VehicleDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String plateNo = resultSet.getString(1);
            String type = resultSet.getString(2);
            String custId = resultSet.getString(3);

            var dto = new VehicleDto(plateNo, type, custId);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<VehicleDto> loadAllVehicles() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM vehicle";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<VehicleDto> vehiList = new ArrayList<>();

        while (resultSet.next()){
            vehiList.add(new VehicleDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return vehiList;
    }
}
