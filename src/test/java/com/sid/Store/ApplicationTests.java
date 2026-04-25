package com.sid.Store;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://wallpaperengineapi.onrender.com";
    }

    @Test
    void contextLoads() {
    }
}