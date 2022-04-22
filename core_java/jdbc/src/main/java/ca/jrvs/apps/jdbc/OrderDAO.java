package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccesObject;
import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DataAccesObject<Order> {

  //put the queries here
  private static final String GET_ONE = "SELECT c.first_name, c.last_name, c.email, o.order_id, "
      + "o.creation_date, o.total_due, o.status, s.first_name, s.last_name, s.email, ol.quantity, "
      + "p.code, p.name, p.size, p.variety, p.price "
      + "FROM orders o join customer c on o.customer_id = c.customer_id "
      + "join salesperson s on o.salesperson_id=s.salesperson_id "
      + "join order_item ol on ol.order_id=o.order_id "
      + "join product p on ol.product_id = p.product_id "
      + "where o.order_id = ?;";

  public OrderDAO(Connection connection) {
    super(connection);
  }


  @Override
  public Order findById(long id) {
    //create new DTO for Order
    Order order = new Order();
    //do the query
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE);) {
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();
      long orderId = 0;
      List<OrderItem> orderItems = new ArrayList<>();
      while (rs.next()) {
        if (orderId == 0) {
          order.setOrderId(rs.getLong(4));
          orderId = order.getId();
          order.setCustomerFirstName(rs.getString(1));
          order.setCustomerLastName(rs.getString(2));
          order.setCustomerEmail(rs.getString(3));
          order.setSalespersonFirstName(rs.getString(8));
          order.setSalespersonLastName(rs.getString(9));
          order.setSalespersonEmail(rs.getString(10));

          order.setCreationDate(rs.getTimestamp(5));
          order.setStatus(rs.getString(7));
          order.setTotalDue(rs.getDouble(6));
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderQuantity(rs.getInt(11));
        orderItem.setProductName(rs.getString(13));
        orderItem.setProductSize(rs.getInt(14));
        orderItem.setProductPrice(rs.getDouble(16));
        orderItem.setProductCode(rs.getString(12));
        orderItem.setProductVariety(rs.getString(15));

        orderItems.add(orderItem);
      }
      order.setOrderLines(orderItems);

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return order;
  }

  @Override
  public List<Order> findAll() {
    return null;
  }

  @Override
  public Order update(Order dto) {
    return null;
  }

  @Override
  public Order create(Order dto) {
    return null;
  }

  @Override
  public void delete(long id) {

  }
}
