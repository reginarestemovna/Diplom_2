package user.update;

import static org.hamcrest.Matchers.equalTo;

import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.UUID;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.create.UserCreateRequest;
import user.create.UserCreateResponse;

@RunWith(Parameterized.class)
public class UserUpdateWithAuthorizationTest extends BaseTest {

  private static final String UPDATE_EMAIL = UUID.randomUUID() + "@test.ru";
  private static final String UPDATE_NAME = UUID.randomUUID().toString();
  private static final String UPDATE_PASSWORD = UUID.randomUUID().toString();

  private final Integer statusCode;
  private final String responseFieldName;

  private final String email;
  private final String name;
  private final String password;
  private final Matcher<Object> responseFieldValueMatcher;

  private UserClient userClient;

  public UserUpdateWithAuthorizationTest(
      Integer statusCode,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      String name,
      String email,
      String password) {
    this.statusCode = statusCode;
    this.responseFieldName = responseFieldName;
    this.responseFieldValueMatcher = responseFieldValueMatcher;
    this.email = email;
    this.name = name;
    this.password = password;
  }

  @Before
  @Override
  public void setUp() {
    super.setUp();
    userClient = new UserClient();
  }

  @Parameterized.Parameters(name = "name: {3}, email: {4}, password: {5}")
  public static Object[][] params() {
    return new Object[][] {
      {200, "success", equalTo(true), UPDATE_NAME, EMAIL, PASSWORD},
      {200, "success", equalTo(true), NAME, UPDATE_EMAIL, PASSWORD},
      {200, "success", equalTo(true), NAME, EMAIL, UPDATE_PASSWORD},
      {200, "success", equalTo(true), NAME, EMAIL, PASSWORD},
    };
  }

  @Test
  @DisplayName("Test user update with authorization of /api/auth/user")
  public void userCreateWithAuthorization() {

    UserCreateRequest userCreateRequest = new UserCreateRequest(NAME, EMAIL, PASSWORD);
    ValidatableResponse createResponse = userClient.create(userCreateRequest);
    UserCreateResponse userCreateResponse = createResponse.extract().as(UserCreateResponse.class);
    String token = userCreateResponse.getAccessToken();

    UserUpdateRequest body = new UserUpdateRequest(name, email, password);
    ValidatableResponse updateResponse = userClient.update(body, token);
    testResponse(updateResponse, responseFieldName, responseFieldValueMatcher, statusCode);

    userClient.deleteTestData(email, password);
  }
}
