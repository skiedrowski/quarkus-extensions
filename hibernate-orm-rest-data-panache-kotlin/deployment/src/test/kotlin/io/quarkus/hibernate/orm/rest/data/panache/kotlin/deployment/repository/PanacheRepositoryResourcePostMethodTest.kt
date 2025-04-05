package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractPostMethodTest
import io.quarkus.test.QuarkusUnitTest
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

internal object PanacheRepositoryResourcePostMethodTest : AbstractPostMethodTest() {
    @RegisterExtension
    val TEST: QuarkusUnitTest? = QuarkusUnitTest()
        .withApplicationRoot(Consumer { jar: JavaArchive? ->
            jar!!
                .addClasses(
                    Collection::class.java, CollectionsResource::class.java, CollectionsRepository::class.java,
                    AbstractEntity::class.java, AbstractItem::class.java, Item::class.java, ItemsResource::class.java,
                    ItemsRepository::class.java
                )
                .addAsResource("application.properties")
                .addAsResource("import.sql")
        })
}
