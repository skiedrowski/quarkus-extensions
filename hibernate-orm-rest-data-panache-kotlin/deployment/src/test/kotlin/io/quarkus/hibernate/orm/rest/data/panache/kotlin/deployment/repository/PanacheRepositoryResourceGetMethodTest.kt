package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractGetMethodTest
import io.quarkus.test.QuarkusUnitTest
import org.junit.jupiter.api.extension.RegisterExtension

internal object PanacheRepositoryResourceGetMethodTest : AbstractGetMethodTest() {
    @RegisterExtension
    val TEST: QuarkusUnitTest = QuarkusUnitTest()
        .withApplicationRoot { jar ->
            jar.addClasses(
                Collection::class.java, CollectionsResource::class.java, CollectionsRepository::class.java,
                AbstractEntity::class.java, AbstractItem::class.java, Item::class.java, ItemsResource::class.java,
                ItemsRepository::class.java, EmptyListItem::class.java, EmptyListItemsRepository::class.java,
                EmptyListItemsResource::class.java
            )
                .addAsResource("application.properties")
                .addAsResource("import.sql")
        }
}
