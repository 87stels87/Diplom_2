import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.*;

public class CreateOrderTest extends BaseTest {
    @Test
    @DisplayName("Создание заказа для авторизованного юзера")
    @Description("Создание заказа для авторизованного юзера")

    public void testCreateOrderByAutorization() {
        User user = User.getRandomFullUser();
        given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_LOGIN_PATH);

        response.then()
                .extract()
                .path("accessToken");

        String token = response.body().path("accessToken");

        Response response1 = given()
                .header("authorization", token)
                .spec(RestAssuredClient.getBaseSpec())
                .body("{\n" +
                        "  \"ingredients\": [\"61c0c5a71d1f82001bdaaa79\",\"61c0c5a71d1f82001bdaaa7a\"]\n" +
                        "}")
                .when().log().all()
                .post(ORDERS_PATH);
        response1.then().assertThat().log().all()
                .body("order.owner.name", equalTo(user.getName()))
                .and()
                .statusCode(HTTP_OK);

    }

    @Test
    @DisplayName("Создание заказа для НЕ авторизованного юзера")
    @Description("Создание заказа для НЕ авторизованного юзера")
    public void testCreateOrderWithoutAutorization() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body("{\n" +
                        "  \"ingredients\": [\"61c0c5a71d1f82001bdaaa79\",\"61c0c5a71d1f82001bdaaa7a\"]\n" +
                        "}")
                .when().log().all()
                .post(ORDERS_PATH);
        response.then().assertThat().log().all()
                .body("order.owner.name", nullValue())
                .and()
                .statusCode(HTTP_OK);

    }

    @Test
    @DisplayName("Создание заказа с невалидным хешом")
    @Description("Создание заказа с невалидным хешом")
    public void testCreateOrderByNotValidHash() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body("{\n" +
                        "  \"ingredients\": [\"61caaa71\",\"61c0c5a71d1aa71\"]\n" +
                        "}")
                .when().log().all()
                .post(ORDERS_PATH);
        response.then().assertThat().log().all()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Создание заказа без ингредиентов")
    public void testCreateOrderByNullIngredient() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body("{\n" +
                        "  \"ingredients\": []\n" +
                        "}")
                .when().log().all()
                .post(ORDERS_PATH);
        response.then().assertThat().log().all()
                .body("success", equalTo(false))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }
}
