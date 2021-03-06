package model.dao.jdbc;

import model.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static model.constants.AttributesHolder.*;

/**
 * Created by User on 5/9/2017.
 */
class ResultSetExtractor {

    protected static OrderProduct getOrderProductFromResultSet(ResultSet resultSet) throws SQLException {
        return OrderProduct.builder()
                .setId(resultSet.getInt(ORDER_PRODUCT_ID_ATTRIBUTE))
                .setOrderId(resultSet.getInt(ORDER_ID_ATTRIBUTE))
                .setProductId(resultSet.getInt(PRODUCT_ID_ATTRIBUTE))
                .setQuantity(resultSet.getInt(QUANTITY))
                .setProductSum(resultSet.getLong(PRODUCT_SUM))
                .build();
    }

    protected static Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .setId(resultSet.getInt(ORDER_ID_ATTRIBUTE))
                .setOrderStatus(resultSet.getString(ORDER_STATUS_ATTRIBUTE))
                .setDate(resultSet.getTimestamp(ORDER_DATE_ATTRIBUTE))
                .setTotalPrice(resultSet.getLong(ORDER_SUM_ATTRIBUTE))
                .build();
    }

    protected static Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .setId(resultSet.getInt(PRODUCT_ID_ATTRIBUTE))
                .setName(resultSet.getString(PRODUCT_NAME_ATTRIBUTE))
                .setDescription(resultSet.getString(PRODUCT_DESCRIPTION_ATTRIBUTE))
                .setPrice(resultSet.getLong(PRODUCT_PRICE_ATTRIBUTE))
                .build();
    }

    protected static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setEmail(resultSet.getString("email"))
                .setPasswordHash(resultSet.getString("password"))
                .setAdmin(resultSet.getBoolean("isadmin"))
                .setBlocked(resultSet.getBoolean("isblocked"))
                .build();
    }

    protected static UserOrder getUserOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return UserOrder.builder()
                .setUserId(resultSet.getInt(USER_ID_ATTRIBUTE))
                .setOrderId(resultSet.getInt(ORDER_ID_ATTRIBUTE))
                .build();
    }
}
