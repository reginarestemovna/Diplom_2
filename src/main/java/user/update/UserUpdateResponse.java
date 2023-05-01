package user.update;

public class UserUpdateResponse {

  private User user;

  private String message;
  private Boolean success;

  public UserUpdateResponse() {}

  public UserUpdateResponse(User user, String message, Boolean success) {
    this.user = user;
    this.message = message;
    this.success = success;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public static class User {
    private String name;
    private String email;

    public User() {}

    public User(String name, String email) {
      this.name = name;
      this.email = email;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }
}
