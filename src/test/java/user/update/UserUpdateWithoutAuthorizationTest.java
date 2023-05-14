package user.update;

import static org.hamcrest.Matchers.equalTo;

import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.UUID;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.create.UserCreateRequest;

@RunWith(Parameterized.class)
public class UserUpdateWithoutAuthorizationTest extends BaseTest {

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

  public UserUpdateWithoutAuthorizationTest(
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

    UserCreateRequest userCreateRequest = new UserCreateRequest(NAME, EMAIL, PASSWORD);
    userClient.create(userCreateRequest);
  }

  @Parameterized.Parameters(name = "name: {3}, email: {4}, password: {5}")
  public static Object[][] params() {
    return new Object[][] {
      {401, "success", equalTo(false), UPDATE_NAME, EMAIL, PASSWORD},
      {401, "success", equalTo(false), NAME, UPDATE_EMAIL, PASSWORD},
      {401, "success", equalTo(false), NAME, EMAIL, UPDATE_PASSWORD},
      {401, "success", equalTo(false), NAME, EMAIL, PASSWORD},
    };
  }

  @Test
  @DisplayName("Test user update without authorization of /api/auth/user")
  public void userCreateWithAuthorization() {

    UserUpdateRequest body = new UserUpdateRequest(name, email, password);
    ValidatableResponse updateResponse = userClient.update(body, null);
    testResponse(updateResponse, responseFieldName, responseFieldValueMatcher, statusCode);
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(EMAIL, PASSWORD);
  }
}
