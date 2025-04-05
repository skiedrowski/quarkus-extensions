package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.openapi

import io.quarkus.builder.Version
import io.quarkus.maven.dependency.Dependency
import io.quarkus.test.QuarkusProdModeTest
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.List
import java.util.function.Consumer

internal class OpenApiIntegrationTest {
    @Test
    fun testOpenApiForGeneratedResources() {
        RestAssured.given().queryParam("format", "JSON")
            .`when`().get(OpenApiIntegrationTest.Companion.OPEN_API_PATH)
            .then()
            .header("Content-Type", "application/json;charset=UTF-8")
            .body("info.title", Matchers.equalTo<String?>("quarkus-hibernate-orm-rest-data-panache-deployment API"))
            .body("paths.'/collections'", Matchers.hasKey<String?>("get"))
            .body("paths.'/collections'.get.tags", Matchers.hasItem<String?>("CollectionsResource"))
            .body("paths.'/collections'.get.responses.'200'.content.'application/json'.schema.type", Matchers.`is`<String?>("array"))
            .body(
                "paths.'/collections'.get.responses.'200'.content.'application/json'.schema.items.\$ref",
                Matchers.`is`<String?>(OpenApiIntegrationTest.Companion.COLLECTIONS_SCHEMA_REF)
            )
            .body("paths.'/collections'", Matchers.hasKey<String?>("post"))
            .body("paths.'/collections'.post.tags", Matchers.hasItem<String?>("CollectionsResource"))
            .body(
                "paths.'/collections'.post.requestBody.content.'application/json'.schema.\$ref",
                Matchers.`is`<String?>(OpenApiIntegrationTest.Companion.COLLECTIONS_SCHEMA_REF)
            )
            .body(
                "paths.'/collections'.post.responses.'201'.content.'application/json'.schema.\$ref",
                Matchers.`is`<String?>(OpenApiIntegrationTest.Companion.COLLECTIONS_SCHEMA_REF)
            )
            .body("paths.'/collections'.post.security[0].SecurityScheme", Matchers.hasItem<String?>("user"))
            .body("paths.'/collections/{id}'", Matchers.hasKey<String?>("get"))
            .body(
                "paths.'/collections/{id}'.get.responses.'200'.content.'application/json'.schema.\$ref",
                Matchers.`is`<String?>(OpenApiIntegrationTest.Companion.COLLECTIONS_SCHEMA_REF)
            )
            .body("paths.'/collections/{id}'.get.security[0].SecurityScheme", Matchers.hasItem<String?>("user"))
            .body("paths.'/collections/{id}'", Matchers.hasKey<String?>("put"))
            .body(
                "paths.'/collections/{id}'.put.requestBody.content.'application/json'.schema.\$ref",
                Matchers.`is`<String?>(OpenApiIntegrationTest.Companion.COLLECTIONS_SCHEMA_REF)
            )
            .body(
                "paths.'/collections/{id}'.put.responses.'201'.content.'application/json'.schema.\$ref",
                Matchers.`is`<String?>(OpenApiIntegrationTest.Companion.COLLECTIONS_SCHEMA_REF)
            )
            .body("paths.'/collections/{id}'.put.security[0].SecurityScheme", Matchers.hasItem<String?>("superuser"))
            .body("paths.'/collections/{id}'", Matchers.hasKey<String?>("delete"))
            .body("paths.'/collections/{id}'.delete.responses", Matchers.hasKey<String?>("204"))
            .body("paths.'/collections/{id}'.delete.security[0].SecurityScheme", Matchers.hasItem<String?>("admin"))
            .body("paths.'/empty-list-items'", Matchers.hasKey<String?>("get"))
            .body("paths.'/empty-list-items'.get.tags", Matchers.hasItem<String?>("EmptyListItemsResource"))
            .body("paths.'/empty-list-items'", Matchers.hasKey<String?>("post"))
            .body("paths.'/empty-list-items'.post.tags", Matchers.hasItem<String?>("EmptyListItemsResource"))
            .body("paths.'/empty-list-items/{id}'", Matchers.hasKey<String?>("get"))
            .body("paths.'/empty-list-items/{id}'", Matchers.hasKey<String?>("put"))
            .body("paths.'/empty-list-items/{id}'", Matchers.hasKey<String?>("delete"))
            .body("paths.'/items'", Matchers.hasKey<String?>("get"))
            .body("paths.'/items'", Matchers.hasKey<String?>("post"))
            .body("paths.'/items/{id}'", Matchers.hasKey<String?>("get"))
            .body("paths.'/items/{id}'", Matchers.hasKey<String?>("put"))
            .body("paths.'/items/{id}'", Matchers.hasKey<String?>("delete"))
    }

    companion object {
        private const val OPEN_API_PATH = "/q/openapi"
        private const val COLLECTIONS_SCHEMA_REF = "#/components/schemas/Collection"

        @RegisterExtension
        val TEST: QuarkusProdModeTest? = QuarkusProdModeTest()
            .withApplicationRoot(Consumer { jar: JavaArchive? ->
                jar!!
                    .addClasses(
                        Collection::class.java, CollectionsResource::class.java, CollectionsRepository::class.java,
                        AbstractEntity::class.java, AbstractItem::class.java, Item::class.java, ItemsResource::class.java,
                        ItemsRepository::class.java, EmptyListItem::class.java, EmptyListItemsRepository::class.java,
                        EmptyListItemsResource::class.java
                    )
                    .addAsResource("application.properties")
                    .addAsResource("import.sql")
            })
            .setForcedDependencies(
                List.of<Dependency?>(
                    Dependency.of("io.quarkus", "quarkus-smallrye-openapi-deployment", Version.getVersion()),
                    Dependency.of("io.quarkus", "quarkus-jdbc-h2-deployment", Version.getVersion()),
                    Dependency.of("io.quarkus", "quarkus-resteasy-jsonb-deployment", Version.getVersion()),
                    Dependency.of("io.quarkus", "quarkus-security-deployment", Version.getVersion())
                )
            )
            .setRun(true)
    }
}
