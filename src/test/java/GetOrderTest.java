import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrderTest extends BaseTest {
    @Test
    @DisplayName("Отображение заказов для авторизованного пользователя")
    @Description("Отображение заказов для авторизованного пользователя")

    public void testGetOrderUserByAutorization() {
        User user = User.getRandomFullUser();
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);
        Response response1 = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_LOGIN_PATH);

        response1.then()
                .extract()
                .path("accessToken");

        String token = response1.body().path("accessToken");

        Response response2 = given()
                .header("authorization", token)
                .spec(RestAssuredClient.getBaseSpec())
                .when().log().all()
                .get(ORDERS_PATH);
        response2.then().assertThat().log().all()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Отображение заказов для НЕ авторизованного пользователя")
    @Description("Отображение заказов для НЕ авторизованного пользователя")

    public void testGetOrderUserWithoutAutorization() {

        Response response2 = given()
                .spec(RestAssuredClient.getBaseSpec())
                .when().log().all()
                .get(ORDERS_PATH);
        response2.then().assertThat().log().all()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }
}
