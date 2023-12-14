package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.EmployeeDto;
import lk.ijse.Dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public boolean saveSupplier(SupplierDto supDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, supDto.getId());
        pstm.setString(2, supDto.getName());
        pstm.setString(3, supDto.getAddress());
        pstm.setString(4, supDto.getTel());
        pstm.setString(5, supDto.getEmail());
        pstm.setString(6, supDto.getE_id());
        pstm.setString(7, supDto.getDate());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }
    public SupplierDto searchSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE sup_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        SupplierDto dto = null;

        if (resultSet.next()) {
            String sId = resultSet.getString(1);
            String sName = resultSet.getString(2);
            String sAddress = resultSet.getString(3);
            String sTel = resultSet.getString(4);
            String sEmail = resultSet.getString(5);
            String sDate = resultSet.getString(6);
            String eId = resultSet.getString(7);

            dto = new SupplierDto(sId, sName, sAddress, sTel, sEmail, sDate, eId);
        }
        return dto;
    }
    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET name = ?, address = ?, contact = ?, email = ?, e_id = ?, date = ? WHERE sup_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getE_id());
        pstm.setString(6, dto.getDate());
        pstm.setString(7, dto.getId());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }
    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE sup_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public List<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SupplierDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String s_id = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            String s_address = resultSet.getString(3);
            String s_tel = resultSet.getString(4);
            String s_email = resultSet.getString(5);
            String emp_id = resultSet.getString(6);
            String s_date = resultSet.getString(7);

            var dto = new SupplierDto(s_id, s_name, s_address, s_tel, s_email, emp_id, s_date);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public List<SupplierDto> loadAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<SupplierDto> supList = new ArrayList<>();

        while (resultSet.next()){
            supList.add(new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return supList;
    }
}
