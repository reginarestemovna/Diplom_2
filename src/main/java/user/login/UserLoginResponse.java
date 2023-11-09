package user.login;

public class UserLoginResponse {

  private User user;
  private Boolean success;
  private String accessToken;
  private String refreshToken;

  public UserLoginResponse() {
  }

  public UserLoginResponse(User user, Boolean success, String accessToken, String refreshToken) {
    this.user = user;
    this.success = success;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
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
