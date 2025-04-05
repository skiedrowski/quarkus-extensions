package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import org.junit.jupiter.api.Test

abstract class AbstractDeleteMethodTest {
    @Test
    fun shouldNotDeleteNonExistentObject() {
        RestAssured.`when`().delete("/items/100")
            .then().statusCode(404)
    }

    @Test
    fun shouldDeleteObject() {
        RestAssured.`when`().delete("/items/1")
            .then().statusCode(204)
    }
}