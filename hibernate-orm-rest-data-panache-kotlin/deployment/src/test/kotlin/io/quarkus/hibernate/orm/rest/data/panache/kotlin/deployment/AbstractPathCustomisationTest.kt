package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.*
import java.util.stream.Stream

abstract class AbstractPathCustomisationTest {
    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider::class)
    fun testGet(path: String?, accept: String?) {
        RestAssured.given().accept(accept)
            .`when`().get(path)
            .then().statusCode(200)
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider::class)
    fun testCreateAndDelete(path: String?, accept: String?) {
        val id = "test-" + Objects.hash(path, accept)
        val response = RestAssured.given().body("{\"id\": \"" + id + "\", \"name\": \"test collection\"}")
            .and().contentType("application/json")
            .and().accept(accept)
            .`when`().post(path)
            .thenReturn()
        Assertions.assertThat(response.getStatusCode()).isEqualTo(201)
        RestAssured.`when`().delete(path + "/" + id)
            .then().statusCode(204)
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider::class)
    fun testUpdate(path: String?, accept: String?) {
        RestAssured.given().body("{\"id\": \"empty\", \"name\": \"updated collection\"}")
            .and().contentType("application/json")
            .and().accept(accept)
            .`when`().put(path + "/empty")
            .then().statusCode(204)
    }

    internal class TestArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments?> {
            return Stream.of<Arguments?>(
                Arguments.of("/collections", "application/json"),
                Arguments.of("/custom-collections/api", "application/json"),
                Arguments.of("/collections", "application/hal+json"),
                Arguments.of("/custom-collections/api", "application/hal+json")
            )
        }
    }
}
