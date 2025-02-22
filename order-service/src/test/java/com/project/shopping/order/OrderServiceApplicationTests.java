package com.project.shopping.order;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0"));
	
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}	

	@Test
	void shouldCreateOrder() {
		String orderRequest = """
			{
				"skuCode": "Test SKU",
				"quantity": 10,
				"price": 100.0
			}
		""";

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(orderRequest)
			.when()
			.post("/api/order")
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("id", Matchers.notNullValue())
			.body("skuCode", equalTo("Test SKU"))
			.body("quantity", equalTo(10))
			.body("price", equalTo(100.0f));
	}
}
