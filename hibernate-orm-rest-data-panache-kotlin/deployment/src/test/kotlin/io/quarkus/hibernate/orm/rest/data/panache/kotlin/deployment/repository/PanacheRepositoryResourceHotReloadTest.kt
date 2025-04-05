package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractHotReloadTest
import io.quarkus.test.QuarkusDevModeTest
import org.junit.jupiter.api.extension.RegisterExtension

internal class PanacheRepositoryResourceHotReloadTest : AbstractHotReloadTest() {
    override val testArchive: QuarkusDevModeTest? = TEST

    override val resourceClass: Class<*>? = CollectionsResource::class.java

    companion object {
        @RegisterExtension
        val TEST: QuarkusDevModeTest = QuarkusDevModeTest()
            .withApplicationRoot { jar ->
                jar.addClasses(
                    Collection::class.java, CollectionsResource::class.java, CollectionsRepository::class.java,
                    AbstractEntity::class.java, AbstractItem::class.java, Item::class.java
                )
                    .addAsResource("application.properties")
                    .addAsResource("import.sql")
            }
    }
}
