package com.example.sdetapi.api;

import com.example.sdetapi.repository.EmployeeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EmployeeApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        employeeRepository.deleteAll();
    }

    @Test
    void shouldCreateEmployeeSuccessfully() {
        String requestBody = """
                {
                  "name": "Ritika Deshmukh",
                  "email": "ritika@example.com",
                  "department": "QA Automation",
                  "salary": 95000
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/api/employees")
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Ritika Deshmukh"))
                .body("email", equalTo("ritika@example.com"))
                .body(matchesJsonSchemaInClasspath("schemas/employee-schema.json"));
    }

    @Test
    void shouldRejectDuplicateEmployeeEmail() {
        String requestBody = """
                {
                  "name": "QA User",
                  "email": "qa@example.com",
                  "department": "Engineering",
                  "salary": 88000
                }
                """;

        given().contentType(ContentType.JSON).body(requestBody).post("/api/employees").then().statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/api/employees")
        .then()
                .statusCode(409)
                .body("message", containsString("Employee already exists"));
    }

    @Test
    void shouldValidateRequiredFields() {
        String requestBody = """
                {
                  "name": "",
                  "email": "bad-email",
                  "department": "QA",
                  "salary": 50000
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/api/employees")
        .then()
                .statusCode(400)
                .body("message", anyOf(containsString("name"), containsString("email")));
    }

    @Test
    void shouldGetEmployeeById() {
        Integer id = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "Automation Tester",
                          "email": "automation@example.com",
                          "department": "Quality Engineering",
                          "salary": 99000
                        }
                        """)
        .when()
                .post("/api/employees")
        .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
        .when()
                .get("/api/employees/" + id)
        .then()
                .statusCode(200)
                .body("email", equalTo("automation@example.com"));
    }

    @Test
    void shouldReturn404ForMissingEmployee() {
        given()
        .when()
                .get("/api/employees/999")
        .then()
                .statusCode(404)
                .body("message", containsString("Employee not found"));
    }
}
