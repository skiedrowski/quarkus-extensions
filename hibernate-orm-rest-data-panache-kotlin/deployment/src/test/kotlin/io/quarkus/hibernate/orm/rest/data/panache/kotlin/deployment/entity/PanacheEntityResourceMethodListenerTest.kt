package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.test.QuarkusUnitTest
import io.restassured.RestAssured
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class PanacheEntityResourceMethodListenerTest {
    @Order(1)
    @Test
    fun shouldListenersBeCalledWhenCreatingEntities() {
        whenCreateEntity()
        Assertions.assertEquals(1, ON_BEFORE_SAVE_COUNTER.get())
        Assertions.assertEquals(1, ON_AFTER_SAVE_COUNTER.get())
    }

    @Order(2)
    @Test
    fun shouldListenersBeCalledWhenUpdatingEntities() {
        whenUpdateEntity()
        Assertions.assertEquals(1, ON_BEFORE_UPDATE_COUNTER.get())
        Assertions.assertEquals(1, ON_AFTER_UPDATE_COUNTER.get())
    }

    @Order(3)
    @Test
    fun shouldListenersBeCalledWhenDeletingEntities() {
        whenDeleteEntity()
        Assertions.assertEquals(1, ON_BEFORE_DELETE_COUNTER.get())
        Assertions.assertEquals(1, ON_AFTER_DELETE_COUNTER.get())
    }

    private fun whenCreateEntity() {
        val response = RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"name\": \"test-simple\", \"collection\": {\"id\": \"full\"}}")
            .`when`().post("/items")
            .thenReturn()
        org.assertj.core.api.Assertions.assertThat(response.statusCode()).isEqualTo(201)
    }

    private fun whenUpdateEntity() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"1\", \"name\": \"first-test\", \"collection\": {\"id\": \"full\"}}")
            .`when`().put("/items/1")
            .then().statusCode(204)
    }

    private fun whenDeleteEntity() {
        RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"id\": \"1\", \"name\": \"first-test\", \"collection\": {\"id\": \"full\"}}")
            .`when`().delete("/items/1")
            .then().statusCode(204)
    }

    companion object {
        val ON_BEFORE_SAVE_COUNTER: AtomicInteger = AtomicInteger(0)
        val ON_AFTER_SAVE_COUNTER: AtomicInteger = AtomicInteger(0)
        val ON_BEFORE_UPDATE_COUNTER: AtomicInteger = AtomicInteger(0)
        val ON_AFTER_UPDATE_COUNTER: AtomicInteger = AtomicInteger(0)
        val ON_BEFORE_DELETE_COUNTER: AtomicInteger = AtomicInteger(0)
        val ON_AFTER_DELETE_COUNTER: AtomicInteger = AtomicInteger(0)

        @RegisterExtension
        val TEST: QuarkusUnitTest? = QuarkusUnitTest()
            .withApplicationRoot(Consumer { jar: JavaArchive? ->
                jar!!
                    .addClasses(
                        Collection::class.java, CollectionsResource::class.java, AbstractEntity::class.java, AbstractItem::class.java,
                        Item::class.java, ItemsResource::class.java, ItemRestDataResourceMethodListener::class.java
                    )
                    .addAsResource("application.properties")
                    .addAsResource("import.sql")
            })
    }
}
