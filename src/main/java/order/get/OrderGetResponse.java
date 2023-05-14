package order.get;

public class OrderGetResponse {

  private Data[] data;
  private Boolean success;

  public OrderGetResponse(Data[] data, Boolean success) {
    this.data = data;
    this.success = success;
  }

  public Data[] getData() {
    return data;
  }

  public void setData(Data[] data) {
    this.data = data;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public static class Data {
    private String _id;

    public Data(String _id) {
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
