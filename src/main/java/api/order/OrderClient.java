package api.order;

import static api.user.Path.*;

import api.ServerClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import order.create.OrderCreateRequest;
import order.get.OrderGetResponse;

public class OrderClient extends ServerClient {

  @Step("Get all ingredients")
  public ValidatableResponse getIngredients() {
    return sendGetRequest(INGREDIENTS_PATH);
  }

  @Step("Order create")
  public ValidatableResponse create(OrderCreateRequest body, String token) {
    if (token != null) {
      return sendPostRequest(body, ORDER_PATH, token);
    }
    return sendPostRequest(body, ORDER_PATH);
  }

  @Step("Get ingredients list for tests")
  public List<String> getIngredientsList() {
    ValidatableResponse orderGetResponse = getIngredients();
    OrderGetResponse orderListResponse = orderGetResponse.extract().as(OrderGetResponse.class);
    OrderGetResponse.Data[] data = orderListResponse.getData();

    List<String> ingredients = new ArrayList<>();
    for (int i = 0; i < 3 && i <= data.length; i++) {
      ingredients.add(data[i].get_id());
    }

    return ingredients;
  }

  @Step("Get ingredients list for tests")
  public List<String> getInvalidIngredientsList() {
    ValidatableResponse orderGetResponse = getIngredients();
    OrderGetResponse orderListResponse = orderGetResponse.extract().as(OrderGetResponse.class);
    OrderGetResponse.Data[] data = orderListResponse.getData();

    List<String> ingredients = new ArrayList<>();
    for (int i = 0; i < 3 && i <= data.length; i++) {
      ingredients.add(data[i].get_id() + UUID.randomUUID());
    }

    return ingredients;
  }

  @Step("Get orders fro user")
  public ValidatableResponse getOrdersForUser(String token) {
    if (token != null) {
      return sendGetRequest(ORDER_PATH, token);
    }
    return sendGetRequest(ORDER_PATH);
  }
}
