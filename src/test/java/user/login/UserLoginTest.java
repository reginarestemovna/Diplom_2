package user.login;

import static org.hamcrest.Matchers.equalTo;

import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.create.UserCreateRequest;

@RunWith(Parameterized.class)
public class UserLoginTest extends BaseTest {

  private final Integer statusCode;
  private final String responseFieldName;
  private final Matcher<Object> responseFieldValueMatcher;
  private final UserLoginRequest body;

  private UserClient userClient;

  public UserLoginTest(
      Integer statusCode,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      UserLoginRequest body) {
    this.statusCode = statusCode;
    this.responseFieldName = responseFieldName;
    this.responseFieldValueMatcher = responseFieldValueMatcher;
    this.body = body;
  }

  @Before
  @Override
  public void setUp() {
    super.setUp();
    userClient = new UserClient();

    UserCreateRequest userCreateRequest = new UserCreateRequest(NAME, EMAIL, PASSWORD);
    userClient.create(userCreateRequest);
  }

  @Parameterized.Parameters
  public static Object[][] params() {
    return new Object[][] {
      {200, "success", equalTo(true), new UserLoginRequest(EMAIL, PASSWORD)},
      {
        401,
        "message",
        equalTo("email or password are incorrect"),
        new UserLoginRequest("", PASSWORD)
      },
      {401, "message", equalTo("email or password are incorrect"), new UserLoginRequest(EMAIL, "")},
      {
        401,
        "message",
        equalTo("email or password are incorrect"),
        new UserLoginRequest(EMAIL + "test", PASSWORD)
      },
      {
        401,
        "message",
        equalTo("email or password are incorrect"),
        new UserLoginRequest(EMAIL, PASSWORD + "test")
      },
    };
  }

  @Test
  @DisplayName("Test user login of /api/auth/login")
  public void userLogin() {
    ValidatableResponse loginResponse = userClient.login(body);

    testResponse(loginResponse, responseFieldName, responseFieldValueMatcher, statusCode);
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(EMAIL, PASSWORD);
  }
}
