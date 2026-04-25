package com.sid.Store;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SignupTest extends ApplicationTests {

    @Test
    public void validSignupTest() {

        String email = "user" + System.currentTimeMillis() + "@test.com";

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", "Password123");
        payload.put("name", "Test User");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/registerUser")
                .then()
                .statusCode(201) // ✅ FIXED
                .body("email", equalTo(email))
                .body("name", equalTo("Test User"));
    }

    @Test
    public void duplicateEmailTest() {

        String email = "duplicate@test.com";

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", "Password123");
        payload.put("name", "Test User");

        // First call → create user
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/registerUser")
                .then()
                .statusCode(200);

        // Second call → should fail
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/registerUser")
                .then()
                .statusCode(409) // ✅ FIX
                .body(containsString("Email already registered"));
    }

    @Test
    public void emptyFieldsTest() {

        Map<String, Object> payload = new HashMap<>();

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/registerUser")
                .then()
                .statusCode(400)
                .body(containsString("Invalid input data"));
    }
}