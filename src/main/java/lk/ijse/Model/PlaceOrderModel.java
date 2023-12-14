package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.PlaceOrderDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {
    private final ItemModel itemModel = new ItemModel();
    private final SupOrderDetailsModel detailsModel = new SupOrderDetailsModel();

    public boolean placeOrder(PlaceOrderDto dto) throws SQLException {
        boolean result = false;
        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = OrderModel.saveOrder(dto.getOrderId(), dto.getSupId(), dto.getDate());
            if (isOrderSaved) {

                System.out.println("Order Saved");
               boolean isItemUpdated = itemModel.updateItem(dto.getTmList());
                if (isItemUpdated) {
                    System.out.println("Item Updated");
                    boolean isOrderDetailSaved = detailsModel.saveOrderDetail(dto.getOrderId(), dto.getTmList());
                    if (isOrderDetailSaved) {
                        System.out.println("Order Detail Saved");
                        connection.commit();
                        return true;
                    }
                }
            }

        }catch (SQLException e){
            connection.rollback();
        }finally{
            connection.setAutoCommit(true);
        }
        return result;
    }
}
