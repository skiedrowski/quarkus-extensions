package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.test.QuarkusUnitTest
import io.restassured.RestAssured
import io.restassured.http.Header
import org.assertj.core.api.Assertions
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

internal class PanacheEntityResourceHalDisabledTest {
    @Test
    fun shouldHalNotBeSupported() {
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/group/projects/1")
            .then().statusCode(406)
    }

    @Test
    fun shouldNotContainLocationAndLinks() {
        var response = RestAssured.given().accept("application/json")
            .and().contentType("application/json")
            .and().body("{\"name\": \"projectname\"}")
            .`when`().post("/group/projects")
            .thenReturn()
        Assertions.assertThat(response.statusCode()).isEqualTo(201)
        Assertions.assertThat(response.header("Location")).isBlank()
        Assertions.assertThat<Header?>(response.getHeaders().getList("Link")).isEmpty()

        response = RestAssured.given().accept("application/json")
            .`when`().get("/group/projects/projectname")
            .thenReturn()
        Assertions.assertThat(response.statusCode()).isEqualTo(200)
        Assertions.assertThat(response.header("Location")).isBlank()
        Assertions.assertThat<Header?>(response.getHeaders().getList("Link")).isEmpty()
    }

    companion object {
        @RegisterExtension
        val TEST: QuarkusUnitTest? = QuarkusUnitTest()
            .withApplicationRoot(Consumer { jar: JavaArchive? ->
                jar!!
                    .addClasses(Project::class.java, ProjectResource::class.java)
                    .addAsResource("application.properties")
            })
    }
}
