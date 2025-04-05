package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

abstract class AbstractDevModeTest {
    @Test
    fun testGet() {
        RestAssured.`when`().get("/items/1")
            .then().statusCode(200)
    }

    @Test
    fun testCreate() {
        val response = RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"name\": \"test-simple\", \"collection\": {\"id\": \"full\"}}")
            .`when`().post("/items")
            .thenReturn()
        Assertions.assertThat(response.getStatusCode()).isEqualTo(201)
    }
}
