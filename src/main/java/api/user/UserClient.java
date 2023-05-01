package api.user;

import static api.user.Path.*;

import api.ServerClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.create.UserCreateRequest;
import user.create.UserCreateResponse;
import user.login.UserLoginRequest;
import user.login.UserLoginResponse;
import user.update.UserUpdateRequest;

public class UserClient extends ServerClient {

  @Step("User login")
  public ValidatableResponse login(UserLoginRequest body) {
    return sendPostRequest(body, LOGIN_USER_PATH);
  }

  @Step("User create")
  public ValidatableResponse create(UserCreateRequest body) {
    return sendPostRequest(body, CREATE_USER_PATH);
  }

  @Step("User update")
  public ValidatableResponse update(UserUpdateRequest body, String token) {

    if (token != null) {
      return sendPatchRequest(body, UPDATE_USER_PATH, token);
    }
    return sendPatchRequest(body, UPDATE_USER_PATH);
  }

  @Step("User delete")
  public ValidatableResponse delete(String token) {
    if (token != null) {
      return sendDeleteRequest(DELETE_USER_PATH, token);
    }
    return sendDeleteRequest(DELETE_USER_PATH);
  }

  @Step("Delete user test data")
  public void deleteTestData(String email, String password) {
    ValidatableResponse response = login(new UserLoginRequest(email, password));

    UserLoginResponse userLoginResponse = response.extract().as(UserLoginResponse.class);
    delete(userLoginResponse.getAccessToken());
  }

  @Step("Get authorization token")
  public String getToken(String name, String email, String password) {
    ValidatableResponse userResponse = create(new UserCreateRequest(name, email, password));
    UserCreateResponse userCreateResponse = userResponse.extract().as(UserCreateResponse.class);
    return userCreateResponse.getAccessToken();
  }
}
