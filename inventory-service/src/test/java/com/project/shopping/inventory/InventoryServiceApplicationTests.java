package com.project.shopping.inventory;

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
class InventoryServiceApplicationTests {

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
	void shouldCreateInventory() {
		String inventoryRequest = """
			{
				"skuCode": "SKU123",
				"quantity": 100
			}
		""";

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(inventoryRequest)
			.when()
			.post("/api/inventory")
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("id", Matchers.notNullValue())
			.body("skuCode", equalTo("SKU123"))
			.body("quantity", equalTo(100));
	}

	@Test
	void shouldCheckIfInStock() {
		String inventoryRequest = """
			{
				"skuCode": "SKU123",
				"quantity": 100
			}
		""";

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(inventoryRequest)
			.when()
			.post("/api/inventory")
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("id", Matchers.notNullValue())
			.body("skuCode", equalTo("SKU123"))
			.body("quantity", equalTo(100));

		RestAssured.given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/inventory/isInStock?skuCode=SKU123&quantity=50")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body(equalTo("true"));

	}
}
