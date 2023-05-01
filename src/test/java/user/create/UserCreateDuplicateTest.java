package user.create;

import static org.hamcrest.Matchers.equalTo;

import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserCreateDuplicateTest extends BaseTest {

  private UserClient userClient;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    userClient = new UserClient();
  }

  @Test
  @DisplayName("Test user duplicate create of /api/auth/register")
  public void userDuplicateCreate() {

    UserCreateRequest userCreateRequest = new UserCreateRequest(NAME, EMAIL, PASSWORD);

    userClient.create(userCreateRequest);

    ValidatableResponse createResponse = userClient.create(userCreateRequest);

    testResponse(createResponse, "success", equalTo(false), 403);
    testResponse(createResponse, "message", equalTo("User already exists"), 403);
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(EMAIL, PASSWORD);
  }
}
