package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

abstract class AbstractPostMethodTest {
    @Test
    fun shouldCreateSimpleObject() {
        val response = RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"name\": \"test-simple\", \"collection\": {\"id\": \"full\"}}")
            .`when`().post("/items")
            .thenReturn()
        Assertions.assertThat(response.statusCode()).isEqualTo(201)
        Assertions.assertThat(response.header("Location")).isNotBlank()
        val id = response.header("Location").substring(response.header("Location").lastIndexOf("/") + 1)
        val body = response.body().jsonPath()
        Assertions.assertThat(body.getString("id")).isEqualTo(id)
        Assertions.assertThat(body.getString("name")).isEqualTo("test-simple")
    }

    @Test
    fun shouldCreateSimpleHalObject() {
        val response = RestAssured.given().accept("application/hal+json")
            .and().contentType("application/json")
            .and().body("{\"name\": \"test-simple-hal\", \"collection\": {\"id\": \"full\"}}")
            .`when`().post("/items")
            .thenReturn()
        Assertions.assertThat(response.statusCode()).isEqualTo(201)
        Assertions.assertThat(response.header("Location")).isNotBlank()
        val id = response.header("Location").substring(response.header("Location").lastIndexOf("/") + 1)
        val body = response.body().jsonPath()
        Assertions.assertThat(body.getString("id")).isEqualTo(id)
        Assertions.assertThat(body.getString("name")).isEqualTo("test-simple-hal")
        Assertions.assertThat(body.getString("_links.add.href")).endsWith("/items")
        Assertions.assertThat(body.getString("_links.list.href")).endsWith("/items")
        Assertions.assertThat(body.getString("_links.self.href")).endsWith("/items/" + id)
        Assertions.assertThat(body.getString("_links.update.href")).endsWith("/items/" + id)
        Assertions.assertThat(body.getString("_links.remove.href")).endsWith("/items/" + id)
    }

    @Test
    fun shouldCreateComplexObjects() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"test-complex\", \"name\": \"test collection\"}")
            .`when`().post("/collections")
            .then().statusCode(201)
            .and().header("Location", Matchers.endsWith("/test-complex"))
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("test-complex")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("test collection")))
            .and().body("items", Matchers.`is`<MutableCollection<*>?>(Matchers.empty<Any?>()))
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"test-complex\", \"name\": \"test collection\"}")
            .`when`().post("/collections")
            .then().statusCode(409)
    }

    @Test
    fun shouldCreateComplexHalObjects() {
        RestAssured.given().accept("application/hal+json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"test-complex-hal\", \"name\": \"test collection\"}")
            .`when`().post("/collections")
            .then().statusCode(201)
            .and().header("Location", Matchers.endsWith("/test-complex-hal"))
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("test-complex-hal")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("test collection")))
            .and().body("items", Matchers.`is`<MutableCollection<*>?>(Matchers.empty<Any?>()))
            .and().body("_links.add.href", Matchers.endsWith("/collections"))
            .and().body("_links.list.href", Matchers.endsWith("/collections"))
            .and().body("_links.self.href", Matchers.endsWith("/collections/test-complex-hal"))
            .and().body("_links.update.href", Matchers.endsWith("/collections/test-complex-hal"))
            .and().body("_links.remove.href", Matchers.endsWith("/collections/test-complex-hal"))
        RestAssured.given().accept("application/hal+json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"test-complex-hal\", \"name\": \"test collection\"}")
            .`when`().post("/collections")
            .then().statusCode(409)
    }
}
