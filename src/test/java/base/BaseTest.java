package base;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.util.UUID;
import org.hamcrest.Matcher;
import org.junit.Before;

public class BaseTest {
  protected static final String EMAIL = UUID.randomUUID() + "@test.ru";
  protected static final String NAME = UUID.randomUUID().toString();
  protected static final String PASSWORD = UUID.randomUUID().toString();
  private static final String URL = "https://stellarburgers.nomoreparties.site";

  @Before
  public void setUp() {
    RestAssured.baseURI = URL;
  }

  protected void testResponse(
      ValidatableResponse response,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      Integer statusCode) {
    response
        .assertThat()
        .body(responseFieldName, responseFieldValueMatcher)
        .and()
        .statusCode(statusCode);
  }

  protected void testResponse(ValidatableResponse response, Integer statusCode) {
    response.assertThat().statusCode(statusCode);
  }
}
