package api;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class ServerClient {

  @Step("Send get request to server")
  protected ValidatableResponse sendGetRequest(String path) {
    return given().when().get(path).then().log().all();
  }

  @Step("Send get request to server")
  protected ValidatableResponse sendGetRequest(String path, String token) {
    return given().header("Authorization", token).log().all().when().get(path).then().log().all();
  }

  @Step("Send post request to server")
  protected ValidatableResponse sendPostRequest(Object body, String path) {
    return given()
        .header("Content-type", "application/json")
        .log()
        .all()
        .and()
        .body(body)
        .when()
        .post(path)
        .then()
        .log()
        .all();
  }

  @Step("Send post request to server")
  protected ValidatableResponse sendPostRequest(Object body, String path, String token) {
    return given()
        .header("Content-type", "application/json")
        .log()
        .all()
        .header("Authorization", token)
        .log()
        .all()
        .and()
        .body(body)
        .log()
        .all()
        .when()
        .post(path)
        .then()
        .log()
        .all();
  }

  @Step("Send patch request to server")
  protected ValidatableResponse sendPatchRequest(Object body, String path) {
    return given()
        .header("Content-type", "application/json")
        .log()
        .all()
        .and()
        .body(body)
        .log()
        .all()
        .when()
        .patch(path)
        .then()
        .log()
        .all();
  }

  @Step("Send patch request to server")
  protected ValidatableResponse sendPatchRequest(Object body, String path, String token) {
    return given()
        .header("Content-type", "application/json")
        .log()
        .all()
        .header("Authorization", token)
        .log()
        .all()
        .and()
        .body(body)
        .log()
        .all()
        .when()
        .patch(path)
        .then()
        .log()
        .all();
  }

  @Step("Send delete request to server")
  protected ValidatableResponse sendDeleteRequest(String path) {
    return given().when().delete(path).then().log().all();
  }

  @Step("Send delete request to server")
  protected ValidatableResponse sendDeleteRequest(String path, String token) {
    return given().header("Authorization", token).when().delete(path).then().log().all();
  }
}
