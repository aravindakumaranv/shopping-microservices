package com.project.shopping.product;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:7.0.5");
	
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDbContainer.start();
	}	

	@Test
	void shouldCreateProduct() {
		String productRequest = """
			{
				"name": "Test Product",
				"description": "Test Description",
				"price": 100.0,
				"category": "Test Category"
			}
		""";

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(productRequest)
			.when()
			.post("/api/product")
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("id", Matchers.notNullValue())
			.body("name", equalTo("Test Product"))
			.body("description", equalTo("Test Description"))
			.body("price", equalTo(100.0f))
			.body("category", equalTo("Test Category"));
	}
}
