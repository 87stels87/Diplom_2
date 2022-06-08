import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeDataUserTest extends BaseTest {
    @Test
    @DisplayName("Изменение емейла у авторизованного юзера")
    @Description("Изменение емейла у авторизованного юзера")

    public void testChangeEmailUserByAutorization() {
        User user = User.getRandomFullUser();
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);
        response.then()
                .extract()
                .path("accessToken");

        String token = response.body().path("accessToken");
        user.setEmail(user.getEmail().substring(1));

        Response response1 = given()
                .header("authorization", token)
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .patch(USER_PATCH_PATH);
        response1.then().assertThat().log().all()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Изменение имени у авторизованного юзера")
    @Description("Изменение имени у авторизованного юзера")

    public void testChangeNameUserByAutorization() {
        User user = User.getRandomFullUser();
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);
        response.then()
                .extract()
                .path("accessToken");

        String token = response.body().path("accessToken");

        user.setName(user.getName().substring(1));
        Response response1 = given()
                .header("authorization", token)
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .patch(USER_PATCH_PATH);
        response1.then().assertThat().log().all()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Изменение имени и емейла у авторизованного юзера")
    @Description("Изменение имени и емейла у авторизованного юзера")

    public void testChangeNameAndEmailUserByAutorization() {
        User user = User.getRandomFullUser();
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);
        response.then()
                .extract()
                .path("accessToken");

        String token = response.body().path("accessToken");

        user.setName(user.getName().substring(1));
        user.setEmail(user.getEmail().substring(1));
        Response response1 = given()
                .header("authorization", token)
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .patch(USER_PATCH_PATH);
        response1.then().assertThat().log().all()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Изменение имени и емейла у НЕ авторизованного юзера")
    @Description("Изменение имени и емейла у НЕ авторизованного юзера")

    public void testChangeNameAndEmailUserWithoutAutorization() {
        User user = User.getRandomFullUser();
        given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);

        user.setName(user.getName().substring(1));
        user.setEmail(user.getEmail().substring(1));
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .patch(USER_PATCH_PATH);
        response.then().assertThat().log().all()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }
}

