package order.create;

public class OrderCreateResponse {

  public Order order;

  public OrderCreateResponse(Order order) {
    this.order = order;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
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
