package order.create;

import static org.hamcrest.Matchers.equalTo;

import api.order.OrderClient;
import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {
  private String token;
  private UserClient userClient;
  private OrderClient orderClient;
  private List<String> ingredients;

  private final Integer statusCode;
  private final String responseFieldName;
  private final Boolean isWithIngredients;
  private final Boolean isWithAuthorization;
  private final Matcher<Object> responseFieldValueMatcher;

  public OrderCreateTest(
      Integer statusCode,
      Boolean isWithAuthorization,
      Boolean isWithIngredients,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher) {
    this.statusCode = statusCode;
    this.isWithIngredients = isWithIngredients;
    this.isWithAuthorization = isWithAuthorization;
    this.responseFieldName = responseFieldName;
    this.responseFieldValueMatcher = responseFieldValueMatcher;
  }

  @Before
  @Override
  public void setUp() {
    super.setUp();

    userClient = new UserClient();
    orderClient = new OrderClient();

    token = userClient.getToken(NAME, EMAIL, PASSWORD);
    ingredients = orderClient.getIngredientsList();
  }

  @Parameterized.Parameters(name = "token: {1}, ingredients: {2}")
  public static Object[][] params() {
    return new Object[][] {
      {200, true, true, "success", equalTo(true)},
      {400, true, false, "success", equalTo(false)},
      {200, false, true, "success", equalTo(true)},
      {400, false, false, "success", equalTo(false)},
    };
  }

  @Test
  @DisplayName("Test order create of /api/orders")
  public void create() {

    OrderCreateRequest orderCreateRequest = getRequest();

    ValidatableResponse createResponse = getCreateResponse(orderCreateRequest);

    testResponse(createResponse, responseFieldName, responseFieldValueMatcher, statusCode);
  }

  private OrderCreateRequest getRequest() {
    if (isWithIngredients) {
      return new OrderCreateRequest(ingredients);
    }
    return new OrderCreateRequest(new ArrayList<>());
  }

  private ValidatableResponse getCreateResponse(OrderCreateRequest orderCreateRequest) {
    if (isWithAuthorization) {
      return orderClient.create(orderCreateRequest, token);
    }
    return orderClient.create(orderCreateRequest, null);
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(EMAIL, PASSWORD);
  }
}
