package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Order implements DataTransferObject {

  //put all the properties of an order object here
  private long orderId;
  private String customerFirstName;
  private String customerLastName;
  private String customerEmail;
  private Timestamp creationDate;
  private double totalDue;
  private String status;
  private String salespersonFirstName;
  private String salespersonLastName;
  private String salespersonEmail;
  private List<OrderItem> orderLines;

  @Override
  public long getId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public double getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(double totalDue) {
    this.totalDue = totalDue;
  }

  public String getSalespersonFirstName() {
    return salespersonFirstName;
  }

  public void setSalespersonFirstName(String salespersonFirstName) {
    this.salespersonFirstName = salespersonFirstName;
  }

  public String getSalespersonLastName() {
    return salespersonLastName;
  }

  public void setSalespersonLastName(String salespersonLastName) {
    this.salespersonLastName = salespersonLastName;
  }

  public String getSalespersonEmail() {
    return salespersonEmail;
  }

  public void setSalespersonEmail(String salespersonEmail) {
    this.salespersonEmail = salespersonEmail;
  }

  public List<OrderItem> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderItem> orderLines) {
    this.orderLines = orderLines;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Order{" +
        "orderId=" + orderId +
        ",\n customerFirstName='" + customerFirstName + '\'' +
        ",\n customerLastName='" + customerLastName + '\'' +
        ",\n customerEmail='" + customerEmail + '\'' +
        ",\n creationDate=" + creationDate +
        ",\n totalDue=" + totalDue +
        ",\n status='" + status + '\'' +
        ",\n salespersonFirstName='" + salespersonFirstName + '\'' +
        ",\n salespersonLastName='" + salespersonLastName + '\'' +
        ",\n salespersonEmail='" + salespersonEmail + '\'' +
        ",\n orderLines=" + orderLines +
        '}';
  }
}
