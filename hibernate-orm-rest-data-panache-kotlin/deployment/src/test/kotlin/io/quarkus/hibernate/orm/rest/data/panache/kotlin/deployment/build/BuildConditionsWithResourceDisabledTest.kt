package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.build

import io.quarkus.test.QuarkusUnitTest
import io.restassured.RestAssured
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Supplier

class BuildConditionsWithResourceDisabledTest {
    @Test
    fun shouldResourceNotBeFound() {
        RestAssured.given().accept("application/json")
            .`when`().get("/collections")
            .then().statusCode(404)
    }

    companion object {
        @RegisterExtension
        val TEST: QuarkusUnitTest? = QuarkusUnitTest()
            .setArchiveProducer(Supplier {
                ShrinkWrap.create<JavaArchive?>(JavaArchive::class.java)
                    .addClasses(Collection::class.java, CollectionsResource::class.java)
            })
    }
}
