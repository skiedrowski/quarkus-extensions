package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractInjectResourcesMethodTest
import io.quarkus.test.QuarkusUnitTest
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

internal object PanacheEntityResourceInjectResourcesMethodTest : AbstractInjectResourcesMethodTest() {
    @RegisterExtension
    val TEST: QuarkusUnitTest? = QuarkusUnitTest()
        .withApplicationRoot(Consumer { jar: JavaArchive? ->
            jar!!
                .addClasses(
                    PanacheEntityBase::class.java, PanacheEntity::class.java, Collection::class.java, CollectionsResource::class.java,
                    AbstractEntity::class.java, AbstractItem::class.java, Item::class.java, ItemsResource::class.java, InjectionResource::class.java
                )
                .addAsResource("application.properties")
                .addAsResource("import.sql")
        })
}
