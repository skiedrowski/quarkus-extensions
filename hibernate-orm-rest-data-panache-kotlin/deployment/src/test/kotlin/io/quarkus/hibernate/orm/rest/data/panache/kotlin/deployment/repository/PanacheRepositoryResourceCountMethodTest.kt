package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractCountMethodTest
import io.quarkus.test.QuarkusUnitTest
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

internal object PanacheRepositoryResourceCountMethodTest : AbstractCountMethodTest() {
    @RegisterExtension
    val TEST: QuarkusUnitTest? = QuarkusUnitTest()
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
}
