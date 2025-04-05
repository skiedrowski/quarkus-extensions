package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.quarkus.test.QuarkusDevModeTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import java.util.function.Function

abstract class AbstractHotReloadTest {
    protected abstract val testArchive: QuarkusDevModeTest?

    @Test
    fun shouldModifyPathAndDisableHal() {
        this.testArchive!!.modifySourceFile(
            this.resourceClass,
            Function { s: String? -> s!!.replace(".*@ResourceProperties.*".toRegex(), "@ResourceProperties(path = \"col\")") })
        RestAssured.given().accept("application/json")
            .`when`().get("/col")
            .then().statusCode(200)
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/col")
            .then().statusCode(406)
    }

    protected abstract val resourceClass: Class<*>?
}
