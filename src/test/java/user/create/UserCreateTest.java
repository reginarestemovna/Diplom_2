package user.create;

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

@RunWith(Parameterized.class)
public class UserCreateTest extends BaseTest {

  private final String name;
  private final String email;
  private final String password;
  private final Integer statusCode;
  private final String responseFieldName;
  private final Matcher<Object> responseFieldValueMatcher;

  private UserClient userClient;

  public UserCreateTest(
      Integer statusCode,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      String name,
      String email,
      String password) {
    this.statusCode = statusCode;
    this.responseFieldName = responseFieldName;
    this.responseFieldValueMatcher = responseFieldValueMatcher;
    this.name = name;
    this.email = email;
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
      {200, "success", equalTo(true), NAME, EMAIL, PASSWORD},
      {
        403,
        "message",
        equalTo("Email, password and name are required fields"),
        "",
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
      },
      {
        403,
        "message",
        equalTo("Email, password and name are required fields"),
        UUID.randomUUID().toString(),
        "",
        UUID.randomUUID().toString()
      },
      {
        403,
        "message",
        equalTo("Email, password and name are required fields"),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        ""
      },
      {
        403,
        "success",
        equalTo(false),
        "",
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
      },
      {
        403,
        "success",
        equalTo(false),
        UUID.randomUUID().toString(),
        "",
        UUID.randomUUID().toString()
      },
      {
        403,
        "success",
        equalTo(false),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        ""
      },
    };
  }

  @Test
  @DisplayName("Test user create of /api/auth/register")
  public void userCreate() {

    UserCreateRequest userCreateRequest = new UserCreateRequest(name, email, password);
    ValidatableResponse createResponse = userClient.create(userCreateRequest);

    testResponse(createResponse, responseFieldName, responseFieldValueMatcher, statusCode);
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(EMAIL, PASSWORD);
  }
}
