package integration;

import com.juniorsfredo.xtreme_management_api.XtremeManagementApiApplication;
import com.juniorsfredo.xtreme_management_api.infrastructure.config.dotenv.DotenvApplicationContextInitializer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(
        classes = XtremeManagementApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = DotenvApplicationContextInitializer.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestDataLoader.class)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestDataLoader testDataLoader;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        testDataLoader.resetAndPopulateDatabase();
    }

    @Test
    @DisplayName(value = "Given admin user, when get user, then return user details and http status 200 - ok")
    void givenAdminUser_whenGetUser_thenReturnUserDetails() {
        given()
                .header("Authorization", "Bearer " + obtainAccessToken("admin@example.com", "admin123"))
                .pathParam("id", 1)
        .when()
                .get("/users/{id}")
        .then()
                .statusCode(200)
                .body("email", equalTo("admin@example.com"));
    }

    @Test
    @DisplayName(value = "Given invalid token, when get user, then return http status 401 - unauthorized")
    void givenInvalidToken_whenGetUser_thenReturnUnauthorized() {
        given()
                .header("Authorization", "Bearer invalidtoken")
                .pathParam("id", 1)
        .when()
                .get("/users/{id}")
        .then()
                .statusCode(401);
    }

    private String obtainAccessToken(String email, String password) {
        return given()
                .contentType("application/json")
                .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
        .when()
                .post("/auth/login")
        .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }
}
