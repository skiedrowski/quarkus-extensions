package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractDevModeTest
import io.quarkus.test.QuarkusDevModeTest
import org.junit.jupiter.api.extension.RegisterExtension

internal class PanacheRepositoryResourceDevModeTest : AbstractDevModeTest() {
    @RegisterExtension
    var TEST: QuarkusDevModeTest = QuarkusDevModeTest()
        .withApplicationRoot { jar ->
            jar.addClasses(
                Collection::class.java, AbstractEntity::class.java, AbstractItem::class.java, Item::class.java,
                ItemsResource::class.java, ItemsRepository::class.java
            )
                .addAsResource("application.properties")
                .addAsResource("import.sql")
        }
}