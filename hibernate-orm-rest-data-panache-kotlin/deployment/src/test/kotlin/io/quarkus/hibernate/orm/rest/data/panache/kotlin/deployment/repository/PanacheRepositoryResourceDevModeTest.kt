package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractDevModeTest
import io.quarkus.test.QuarkusDevModeTest
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

//object?
class PanacheRepositoryResourceDevModeTest : AbstractDevModeTest() {
    //@JvmStatic ??
    @RegisterExtension
    var TEST: QuarkusDevModeTest? = QuarkusDevModeTest()
        .withApplicationRoot(Consumer { jar: JavaArchive? ->
            jar!!
                .addClasses(
                    Collection::class.java, AbstractEntity::class.java, AbstractItem::class.java, Item::class.java,
                    ItemsResource::class.java, ItemsRepository::class.java
                )
                .addAsResource("application.properties")
                .addAsResource("import.sql")
        })
}