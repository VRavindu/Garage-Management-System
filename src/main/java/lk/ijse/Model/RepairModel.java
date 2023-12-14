package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.CustomerDto;
import lk.ijse.Dto.RepairDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairModel {
    public boolean saveRepair(RepairDto repairDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO repair VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, repairDto.getId());
        pstm.setString(2, repairDto.getStatus());
        pstm.setString(3, repairDto.getCost());
        pstm.setString(4, repairDto.getPlateNo());
        pstm.setString(5, repairDto.getEmpId());
        pstm.setString(6, repairDto.getDate());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }

    public RepairDto searchRepair(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM repair WHERE r_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        RepairDto dto = null;

        if (resultSet.next()) {
            String rId = resultSet.getString(1);
            String status = resultSet.getString(2);
            String cost = resultSet.getString(3);
            String plateNo = resultSet.getString(4);
            String empId = resultSet.getString(5);
            String date = resultSet.getString(6);

            dto = new RepairDto(rId, status, cost, plateNo, empId, date);
        }
        return dto;
    }

    public boolean updateRepair(RepairDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE repair SET status = ?, cost = ?, plate_no = ?, e_id = ?, date = ? WHERE r_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getStatus());
        pstm.setString(2, dto.getCost());
        pstm.setString(3, dto.getPlateNo());
        pstm.setString(4, dto.getEmpId());
        pstm.setString(5, dto.getDate());
        pstm.setString(6, dto.getId());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }

    public List<RepairDto> getAllRepairs() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM repair";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<RepairDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String rId = resultSet.getString(1);
            String status = resultSet.getString(2);
            String cost = resultSet.getString(3);
            String plateNo = resultSet.getString(4);
            String emp_id = resultSet.getString(5);
            String date = resultSet.getString(6);

            var dto = new RepairDto(rId, status, cost, plateNo, emp_id, date);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
