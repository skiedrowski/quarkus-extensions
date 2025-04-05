package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

abstract class AbstractPutMethodTest {
    @Test
    fun shouldUpdateSimpleObject() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"1\", \"name\": \"first-test\", \"collection\": {\"id\": \"full\"}}")
            .`when`().put("/items/1")
            .then().statusCode(204)
        RestAssured.given().accept("application/json")
            .`when`().get("/items/1")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<Int?>(Matchers.equalTo<Int?>(1)))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("first-test")))
    }

    @Test
    fun shouldUpdateComplexObject() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"empty\", \"name\": \"updated collection\"}")
            .`when`().put("/collections/empty")
            .then().statusCode(204)
        RestAssured.given().accept("application/json")
            .`when`().get("/collections/empty")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("empty")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("updated collection")))
    }

    @Test
    fun shouldNotUpdateSimpleObjectId() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"100\", \"name\": \"second\", \"collection\": {\"id\": \"full\"}}")
            .`when`().put("/items/2")
            .then().statusCode(204)
        RestAssured.given().accept("application/json")
            .`when`().get("/items/2")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<Int?>(Matchers.equalTo<Int?>(2)))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("second")))
        RestAssured.given().accept("application/json")
            .`when`().get("/items/100")
            .then().statusCode(404)
    }

    @Test
    fun shouldNotUpdateComplexObjectId() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"updated-empty\", \"name\": \"empty collection\"}")
            .`when`().put("/collections/empty")
            .then().statusCode(204)
        RestAssured.given().accept("application/json")
            .`when`().get("/collections/empty")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("empty")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("empty collection")))
        RestAssured.given().accept("application/json")
            .`when`().get("/collections/updated-empty")
            .then().statusCode(404)
    }

    @Test
    fun shouldCreateObjectWithRequiredId() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"test-collection\", \"name\": \"test collection\"}")
            .`when`().post("/collections")
            .then().statusCode(201)
            .and().header("Location", Matchers.endsWith("/collections/test-collection"))
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("test-collection")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("test collection")))
            .and().body("items", Matchers.`is`<MutableCollection<*>?>(Matchers.empty<Any?>()))
    }

    @Test
    fun shouldCreateHalObjectWithRequiredId() {
        RestAssured.given().accept("application/hal+json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"test-collection-hal\", \"name\": \"test collection\"}")
            .`when`().post("/collections")
            .then().statusCode(201)
            .and().header("Location", Matchers.endsWith("/collections/test-collection-hal"))
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("test-collection-hal")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("test collection")))
            .and().body("items", Matchers.`is`<MutableCollection<*>?>(Matchers.empty<Any?>()))
            .and().body("_links.add.href", Matchers.endsWith("/collections"))
            .and().body("_links.list.href", Matchers.endsWith("/collections"))
            .and().body("_links.self.href", Matchers.endsWith("/collections/test-collection-hal"))
            .and().body("_links.update.href", Matchers.endsWith("/collections/test-collection-hal"))
            .and().body("_links.remove.href", Matchers.endsWith("/collections/test-collection-hal"))
    }

    @Test
    fun shouldCreateObjectWithGeneratedId() {
        val response = RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"name\": \"test-item\", \"collection\": {\"id\": \"full\"}}")
            .`when`().post("/items")
            .thenReturn()
        Assertions.assertThat(response.statusCode()).isEqualTo(201)
        Assertions.assertThat(response.header("Location")).isNotBlank()
        val id = response.header("Location").substring(response.header("Location").lastIndexOf("/") + 1)
        val body = response.body().jsonPath()
        Assertions.assertThat(body.getString("id")).isEqualTo(id)
        Assertions.assertThat(body.getString("name")).isEqualTo("test-item")
    }

    @Test
    fun shouldCreateHalObjectWithGeneratedId() {
        val response = RestAssured.given().accept("application/hal+json")
            .and().contentType("application/json")
            .and().body("{\"name\": \"test-item-hal\", \"collection\": {\"id\": \"full\"}}")
            .`when`().post("/items")
            .thenReturn()
        Assertions.assertThat(response.statusCode()).isEqualTo(201)
        Assertions.assertThat(response.header("Location")).isNotBlank()
        val id = response.header("Location").substring(response.header("Location").lastIndexOf("/") + 1)
        val body = response.body().jsonPath()
        Assertions.assertThat(body.getString("id")).isEqualTo(id)
        Assertions.assertThat(body.getString("name")).isEqualTo("test-item-hal")
        Assertions.assertThat(body.getString("_links.add.href")).endsWith("/items")
        Assertions.assertThat(body.getString("_links.list.href")).endsWith("/items")
        Assertions.assertThat(body.getString("_links.self.href")).endsWith("/items/" + id)
        Assertions.assertThat(body.getString("_links.update.href")).endsWith("/items/" + id)
        Assertions.assertThat(body.getString("_links.remove.href")).endsWith("/items/" + id)
    }
}
