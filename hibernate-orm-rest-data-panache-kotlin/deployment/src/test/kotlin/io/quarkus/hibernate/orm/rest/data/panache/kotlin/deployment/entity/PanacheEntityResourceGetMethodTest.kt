package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractGetMethodTest
import io.quarkus.test.QuarkusUnitTest
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

internal class PanacheEntityResourceGetMethodTest : AbstractGetMethodTest() {
    @Test
    fun shouldCopyAdditionalMethodsAsResources() {
        RestAssured.given().accept("application/json")
            .`when`().get("/collections/name/full collection")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<String?>("full"))
            .and().body("name", Matchers.`is`<String?>("full collection"))
    }

    @Test
    fun shouldAdditionalMethodsSupportHal() {
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/collections/name/full collection")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<String?>("full"))
            .and().body("name", Matchers.`is`<String?>("full collection"))
            .and().body("_links.addByName.href", Matchers.containsString("/name/full"))
    }

    @Test
    fun shouldReturnItemsForFullCollection() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items?collection.id=full")
            .then().statusCode(200)
            .body("$", Matchers.hasSize<Any?>(2))
    }

    @Test
    fun shouldReturnNoItemsForEmptyCollection() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items?collection.id=empty")
            .then().statusCode(200)
            .body("$", Matchers.hasSize<Any?>(0))
    }

    companion object {
        @RegisterExtension
        val TEST: QuarkusUnitTest? = QuarkusUnitTest()
            .withApplicationRoot(Consumer { jar: JavaArchive? ->
                jar!!
                    .addClasses(
                        Collection::class.java, CollectionsResource::class.java, AbstractEntity::class.java, AbstractItem::class.java,
                        Item::class.java, ItemsResource::class.java,
                        EmptyListItem::class.java, EmptyListItemsResource::class.java
                    )
                    .addAsResource("application.properties")
                    .addAsResource("import.sql")
            })
    }
}
