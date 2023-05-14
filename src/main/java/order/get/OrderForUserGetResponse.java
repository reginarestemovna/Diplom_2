package order.get;

public class OrderForUserGetResponse {

  private Order[] orders;
  private Boolean success;

  public OrderForUserGetResponse(Order[] orders, Boolean success) {
    this.orders = orders;
    this.success = success;
  }

  public Order[] getOrders() {
    return orders;
  }

  public void setOrders(Order[] orders) {
    this.orders = orders;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public static class Order {
    private String _id;

    public Order(String _id) {
      this._id = _id;
    }

    public String get_id() {
      return _id;
    }

    public void set_id(String _id) {
      this._id = _id;
    }
  }
}
