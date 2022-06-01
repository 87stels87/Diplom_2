import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends BaseTest {
    @Test
    @DisplayName("Создание нового юзера")
    @Description("Создание нового юзера")

    public void testCreateNewUser() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(User.getRandomFullUser())
                .when().log().all()
                .post(USER_CREATE_PATH);
        response.then()
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Создание юзера с идентичными данными")
    @Description("Создание юзера с идентичными данными")
    public void testCreateIdentialUser() {
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
                .post(USER_CREATE_PATH);
        response.then()
                .assertThat()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
    }

    @Test
    @DisplayName("Создание юзера без пароля")
    @Description("Создание юзера без пароля")

    public void testCreateNewUserWithoutPassword() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(User.getRandomUserWithoutPassword())
                .when().log().all()
                .post(USER_CREATE_PATH);
        response.then()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
    }

    @Test
    @DisplayName("Создание юзера без имени")
    @Description("Создание юзера без имени")

    public void testCreateNewUserWithoutName() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(User.getRandomUserWithoutName())
                .when().log().all()
                .post(USER_CREATE_PATH);
        response.then()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
    }

    @Test
    @DisplayName("Создание юзера без емейл")
    @Description("Создание юзера без емейл")

    public void testCreateNewUserWithoutEmail() {
        Response response = given()
                .spec(RestAssuredClient.getBaseSpec())
                .body(User.getRandomUserWithoutEmail())
                .when().log().all()
                .post(USER_CREATE_PATH);
        response.then()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
    }
}

