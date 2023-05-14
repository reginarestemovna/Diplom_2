package user.update;

import static org.hamcrest.Matchers.equalTo;

import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.create.UserCreateRequest;
import user.create.UserCreateResponse;

public class UserUpdateWithInvalidFieldsTest extends BaseTest {

  private static final String FIRST_NAME = UUID.randomUUID().toString();
  private static final String FIRST_EMAIL = UUID.randomUUID() + "@test.ru";
  private static final String FIRST_PASSWORD = UUID.randomUUID().toString();

  private static final String SECOND_NAME = UUID.randomUUID().toString();
  private static final String SECOND_EMAIL = UUID.randomUUID() + "@test.ru";
  private static final String SECOND_PASSWORD = UUID.randomUUID().toString();

  private UserClient userClient;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    userClient = new UserClient();
  }

  @Test
  @DisplayName("Test user update with invalid email of /api/auth/user")
  public void userCreateWithAuthorization() {

    createFirstUser();
    String token = createSecondUserAndGetToken();

    UserUpdateRequest body = new UserUpdateRequest(FIRST_NAME, FIRST_EMAIL, FIRST_PASSWORD);
    ValidatableResponse updateResponse = userClient.update(body, token);
    testResponse(updateResponse, "success", equalTo(false), 403);
  }

  private void createFirstUser() {
    UserCreateRequest firstUserCreateRequest =
        new UserCreateRequest(FIRST_NAME, FIRST_EMAIL, FIRST_PASSWORD);
    userClient.create(firstUserCreateRequest);
  }

  private String createSecondUserAndGetToken() {
    UserCreateRequest secondUserCreateRequest =
        new UserCreateRequest(SECOND_NAME, SECOND_EMAIL, SECOND_PASSWORD);
    ValidatableResponse createResponse = userClient.create(secondUserCreateRequest);
    UserCreateResponse userCreateResponse = createResponse.extract().as(UserCreateResponse.class);
    return userCreateResponse.getAccessToken();
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(FIRST_EMAIL, FIRST_PASSWORD);
    userClient.deleteTestData(SECOND_EMAIL, SECOND_PASSWORD);
  }
}
