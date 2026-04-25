package com.sid.Store;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest extends ApplicationTests {

    private final String email = "test@example.com";
    private final String password = "Password123";

    @BeforeEach
    public void setupUser() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("name", "Test User");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/registerUser");
    }

    @Test
    public void validLoginTest() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

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

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", "wrongpass");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/loginUser")
                .then()
                .statusCode(401)
                .body(containsString("Invalid password")); // ✅ FIXED
    }

    @Test
    public void emptyFieldsTest() {

        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/loginUser") // ✅ FIXED
                .then()
                .statusCode(400)
                .body(containsString("Invalid input data"));
    }
}