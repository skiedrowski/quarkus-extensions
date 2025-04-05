package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.build

import io.quarkus.test.QuarkusUnitTest
import io.restassured.RestAssured
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class BuildConditionsWithResourceEnabledTest {
    @Test
    fun shouldResourceBeFound() {
        RestAssured.given().accept("application/json")
            .`when`().get("/collections")
            .then().statusCode(HttpStatus.SC_OK)
    }

    @Test
    fun shouldGetTotalNumberOfEntities() {
        RestAssured.given()
            .`when`().get("/collections/count")
            .then().statusCode(HttpStatus.SC_OK)
            .and().body(Matchers.`is`<String?>(Matchers.equalTo<String?>("2")))
    }

    companion object {
        @RegisterExtension
        @JvmField
        val runner = QuarkusUnitTest()
            .overrideConfigKey("collections.enabled", "true")
            .setArchiveProducer {
                ShrinkWrap.create<JavaArchive?>(JavaArchive::class.java)
                    .addClasses(Collection::class.java, CollectionsResource::class.java)
            }
    }
}
