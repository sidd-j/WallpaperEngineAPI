package com.sid.Store;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest extends ApplicationTests {

    @Test
    public void validLoginTest() {
        String payload = """
        {
            "email": "test@example.com",
            "password": "Password123"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/loginUser")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void invalidPasswordTest() {
        String payload = """
        {
            "email": "test@example.com",
            "password": "wrongpass"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/loginUser")
                .then()
                .statusCode(401)
                .body("message", containsString("Invalid"));
    }

    @Test
    public void emptyFieldsTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/registerUser")
                .then()
                .statusCode(400);
    }
}