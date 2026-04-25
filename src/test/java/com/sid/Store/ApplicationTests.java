package com.sid.Store;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.BeforeClass;


@SpringBootTest
@ActiveProfiles("test")
class ApplicationTests {
    @Test
    void contextLoads() {}

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://wallpaperengineapi.onrender.com"; // change if deployed
    }
}