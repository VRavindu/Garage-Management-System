package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.CustomerDto;
import lk.ijse.Dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public boolean saveCustomer(CustomerDto custDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, custDto.getId());
        pstm.setString(2, custDto.getName());
        pstm.setString(3, custDto.getAddress());
        pstm.setString(4, custDto.getTel());
        pstm.setString(5, custDto.getE_id());
        pstm.setString(6, custDto.getDate());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }

    public CustomerDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE c_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if (resultSet.next()) {
            String cId = resultSet.getString(1);
            String cName = resultSet.getString(2);
            String cAddress = resultSet.getString(3);
            String cTel = resultSet.getString(4);
            String cDate = resultSet.getString(5);
            String eId = resultSet.getString(6);

            dto = new CustomerDto(cId, cName, cAddress, cTel, cDate, eId);
        }
        return dto;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET c_name = ?, c_address = ?, c_contact = ?, e_id = ?, date = ? WHERE c_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getE_id());
        pstm.setString(5, dto.getDate());
        pstm.setString(6, dto.getId());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE c_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public List<CustomerDto> getAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CustomerDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String cust_id = resultSet.getString(1);
            String cust_name = resultSet.getString(2);
            String cust_address = resultSet.getString(3);
            String cust_tel = resultSet.getString(4);
            String emp_id = resultSet.getString(5);
            String cust_date = resultSet.getString(6);

            var dto = new CustomerDto(cust_id, cust_name, cust_address, cust_tel, emp_id, cust_date);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<CustomerDto> loadAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CustomerDto> custList = new ArrayList<>();

        while (resultSet.next()){
            custList.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return custList;
    }
}
