package order.get;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import api.order.OrderClient;
import api.user.UserClient;
import base.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import order.create.OrderCreateRequest;
import order.create.OrderCreateResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderGetTest extends BaseTest {

  private String token;
  private UserClient userClient;
  private OrderClient orderClient;

  @Before
  @Override
  public void setUp() {
    super.setUp();

    userClient = new UserClient();
    orderClient = new OrderClient();

    token = userClient.getToken(NAME, EMAIL, PASSWORD);
  }

  @Test
  @DisplayName("Test order get with authorization of /api/orders")
  public void createWithAuthorization() {

    String orderId = createOrderAndGetId();
    createOrder();
    createOrder();

    ValidatableResponse ordersForUserResponse = orderClient.getOrdersForUser(token);
    OrderForUserGetResponse.Order[] orders = getOrders(ordersForUserResponse);

    assertTrue(orders.length <= 50);
    assertEquals(orders[0].get_id(), orderId);

    testResponse(ordersForUserResponse, "success", equalTo(true), 200);
  }

  @Test
  @DisplayName("Test order get without authorization of /api/orders")
  public void createWithOutAuthorization() {

    createOrder();

    ValidatableResponse ordersForUserResponse = orderClient.getOrdersForUser(null);

    testResponse(ordersForUserResponse, "success", equalTo(false), 401);
  }

  private ValidatableResponse createOrder() {
    List<String> ingredientsList = orderClient.getIngredientsList();
    return orderClient.create(new OrderCreateRequest(ingredientsList), token);
  }

  private String createOrderAndGetId() {
    ValidatableResponse validatableOrderCreateResponse = createOrder();
    OrderCreateResponse orderCreateResponse =
        validatableOrderCreateResponse.extract().as(OrderCreateResponse.class);
    return orderCreateResponse.getOrder().get_id();
  }

  private OrderForUserGetResponse.Order[] getOrders(ValidatableResponse response) {
    OrderForUserGetResponse orderForUserGetResponse =
        response.extract().as(OrderForUserGetResponse.class);

    return orderForUserGetResponse.getOrders();
  }

  @After
  public void cleanup() {
    userClient.deleteTestData(EMAIL, PASSWORD);
  }
}
