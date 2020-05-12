package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured.given().when().get("/todo").then().statusCode(200);
	}

	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
				.body("{\n" + "  \"task\": \"Teste via API\",\n" + "  \"dueDate\": \"2020-12-30\"\n" + "}")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
				.body("{\n" + "  \"task\": \"Teste via API\",\n" + "  \"dueDate\": \"2010-12-30\"\n" + "}")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.log().all()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"))
			;
	}

}
