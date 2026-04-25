package com.sid.Store;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SignupTest extends ApplicationTests {

    @Test
    public void validSignupTest() {
        String payload = """
        {
            "email": "newuser@test.com",
            "password": "Password123"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/signup")
                .then()
                .statusCode(201)
                .body("message", containsString("success"));
    }

    @Test
    public void duplicateEmailTest() {
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
                .post("/signup")
                .then()
                .statusCode(409);
    }
}