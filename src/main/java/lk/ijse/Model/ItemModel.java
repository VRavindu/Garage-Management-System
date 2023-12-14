package lk.ijse.Model;

import javafx.scene.chart.XYChart;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.ItemDto;
import lk.ijse.Dto.tm.PlaceOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public boolean saveItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO item_stock VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItem_code());
        pstm.setString(2, dto.getQty());
        pstm.setString(3, dto.getPrice());
        pstm.setString(4, dto.getDesc());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }

    public boolean updateItem(final ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item_stock SET qty = ?, price = ?, description = ? WHERE item_code = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getQty());
        pstm.setString(2, dto.getPrice());
        pstm.setString(3, dto.getDesc());
        pstm.setString(4, dto.getItem_code());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }

    public boolean updateItem(List<PlaceOrderTm> tmList) throws SQLException {
        for (PlaceOrderTm placeOrderTm : tmList) {
            if(!updateQty(placeOrderTm)) {
                return false;
            }
        }
        return true;
    }

    public boolean deleteItem(String code) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM item_stock WHERE item_code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }

    public ItemDto searchItem(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item_stock WHERE item_code = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();

        ItemDto dto = null;

        if (resultSet.next()){
            String iCode = resultSet.getString(1);
            String iQty = resultSet.getString(2);
            String iPrice = resultSet.getString(3);
            String iDesc = resultSet.getString(4);

            dto = new ItemDto(iCode, iQty, iPrice, iDesc);
        }
        return dto;
    }
    public List<ItemDto> getAllItem() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ItemDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String code = resultSet.getString(1);
            String qty = resultSet.getString(2);
            String price = resultSet.getString(3);
            String desc = resultSet.getString(4);

            var dto = new ItemDto(code, qty, price, desc);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public List<ItemDto> loadAllItem() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item_stock";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<ItemDto> itemList = new ArrayList<>();

        while (resultSet.next()){
            itemList.add(new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }
        return itemList;
    }

    private boolean updateQty(PlaceOrderTm orderTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item_stock SET qty = qty + ? WHERE item_code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderTm.getQty());
        pstm.setString(2, orderTm.getCode());

        return pstm.executeUpdate() > 0;
    }

    public  XYChart.Series getItemChart() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT qty , description FROM item_stock;";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        XYChart.Series series = new XYChart.Series();
        while (resultSet.next()){
            int tQty = resultSet.getInt(1);
            String tDesc = resultSet.getString(2);

            series.getData().add(new XYChart.Data(tDesc, tQty));

        }
        return series;
    }
}
