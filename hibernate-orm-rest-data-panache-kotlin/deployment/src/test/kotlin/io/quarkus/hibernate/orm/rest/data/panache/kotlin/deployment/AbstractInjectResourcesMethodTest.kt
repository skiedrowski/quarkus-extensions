package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

abstract class AbstractInjectResourcesMethodTest {
    @Test
    fun shouldGetListOfItems() {
        RestAssured.given().accept("application/json")
            .`when`().get("/call/resource/items")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(1, 2))
    }
}
