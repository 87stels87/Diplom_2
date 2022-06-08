import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest extends BaseTest {
    @Test
    @DisplayName("Авторизация зарегестрированного юзера")
    @Description("Авторизация зарегестрированного юзера")

    public void testLoginRegistrationUser() {
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
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Авторизация НЕзарегестрированного юзера")
    @Description("Авторизация НЕзарегестрированного юзера")

    public void testLoginNotRegistrationUser() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(User.getRandomFullUser())
                .when().log().all()
                .post(USER_LOGIN_PATH);
        response.then()
                .assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Авторизация зарегестрированного юзера с неверным паролем")
    @Description("Авторизация зарегестрированного юзера с неверным паролем")

    public void testLoginRegistrationUserNotValidPassword() {

        User user = User.getRandomFullUser();
        given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);
        user.setPassword(user.getPassword().toUpperCase());
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_LOGIN_PATH);
        response.then()
                .assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Авторизация зарегестрированного юзера с неверным емейлом")
    @Description("Авторизация зарегестрированного юзера с неверным емейлом")
    public void testLoginRegistrationUserNotValidEmail() {

        User user = User.getRandomFullUser();

        given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_CREATE_PATH);
        user.setEmail(user.getEmail().substring(1));
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(user)
                .when().log().all()
                .post(USER_LOGIN_PATH);
        response.then()
                .assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }
}
