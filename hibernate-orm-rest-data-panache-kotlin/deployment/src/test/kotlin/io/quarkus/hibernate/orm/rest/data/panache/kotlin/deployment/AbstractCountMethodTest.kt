package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

abstract class AbstractCountMethodTest {
    @Test
    fun shouldGetTotalNumberOfEntities() {
        RestAssured.given().get("/collections/count")
            .then().statusCode(HttpStatus.SC_OK)
            .and().body(Matchers.`is`<String?>(Matchers.equalTo<String?>("2")))
    }
}
