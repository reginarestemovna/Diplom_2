package order.create;

import api.order.OrderClient;
import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderCreateWithInvalidIngredientsTest extends BaseTest {

  private static String token;
  private static UserClient userClient;
  private static OrderClient orderClient;
  private static List<String> ingredients;

  @Before
  @Override
  public void setUp() {
    super.setUp();

    userClient = new UserClient();
    orderClient = new OrderClient();

    token = userClient.getToken(NAME, EMAIL, PASSWORD);
    ingredients = orderClient.getInvalidIngredientsList();
  }

  @Test
  @DisplayName("Test order create with authorization and with invalid ingredients of /api/orders")
  public void createWithAuthorizationAndWithInvalidIngredients() {

    OrderCreateRequest orderCreateRequest = new OrderCreateRequest(ingredients);

    ValidatableResponse createResponse = orderClient.create(orderCreateRequest, token);

    testResponse(createResponse, 500);
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(EMAIL, PASSWORD);
  }
}
