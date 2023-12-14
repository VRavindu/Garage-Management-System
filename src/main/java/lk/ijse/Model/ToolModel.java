package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.ToolDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToolModel {
    public boolean saveTool(ToolDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tools VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getT_code());
        pstm.setString(2, dto.getDesc());
        pstm.setString(3, dto.getStatus());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }

    public boolean updateTool(final ToolDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tools SET description = ?, status = ? WHERE t_code = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDesc());
        pstm.setString(2, dto.getStatus());
        pstm.setString(3, dto.getT_code());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }

    public boolean deleteTool(String code) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM tools WHERE t_code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }

    public ToolDto searchTool(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tools WHERE t_code = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();

        ToolDto dto = null;

        if (resultSet.next()){
            String tCode = resultSet.getString(1);
            String tDesc = resultSet.getString(2);
            String tStatus = resultSet.getString(3);

            dto = new ToolDto(tCode, tDesc, tStatus);
        }
        return dto;
    }
    public List<ToolDto> getAllTools() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tools";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ToolDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String code = resultSet.getString(1);
            String desc = resultSet.getString(2);
            String status = resultSet.getString(3);

            var dto = new ToolDto(code, desc, status);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public List<ToolDto> loadAllTool() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tools";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<ToolDto> toolList = new ArrayList<>();

        while (resultSet.next()){
            toolList.add(new ToolDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
        }
        return toolList;
    }
}
