package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractPostMethodTest
import io.quarkus.test.QuarkusUnitTest
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

internal class PanacheEntityResourcePostMethodTest : AbstractPostMethodTest() {
    @Test
    fun shouldCopyUserMethodsAnnotatedWithTransactional() {
        RestAssured.given().accept("application/json")
            .`when`().post("/collections/name/mycollection")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<String?>("mycollection"))
            .and().body("name", Matchers.`is`<String?>("mycollection"))
    }

    companion object {
        @RegisterExtension
        val TEST: QuarkusUnitTest? = QuarkusUnitTest()
            .withApplicationRoot(Consumer { jar: JavaArchive? ->
                jar!!
                    .addClasses(
                        Collection::class.java, CollectionsResource::class.java, AbstractEntity::class.java, AbstractItem::class.java,
                        Item::class.java, ItemsResource::class.java
                    )
                    .addAsResource("application.properties")
                    .addAsResource("import.sql")
            })
    }
}
