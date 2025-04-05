package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheEntityResource
import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractPathCustomisationTest
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import io.quarkus.rest.data.panache.kotlin.MethodProperties
import io.quarkus.rest.data.panache.kotlin.ResourceProperties
import io.quarkus.test.QuarkusUnitTest
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.function.Consumer

internal object PanacheEntityResourcePathCustomisationTest : AbstractPathCustomisationTest() {
    @RegisterExtension
    val TEST: QuarkusUnitTest? = QuarkusUnitTest()
        .withApplicationRoot(Consumer { jar: JavaArchive? ->
            jar!!
                .addClasses(
                    Collection::class.java, CollectionsResource::class.java, AbstractEntity::class.java, AbstractItem::class.java,
                    Item::class.java, CustomPathCollectionsResource::class.java
                )
                .addAsResource("application.properties")
                .addAsResource("import.sql")
        })

    @ResourceProperties(path = "custom-collections", hal = true)
    interface CustomPathCollectionsResource : PanacheEntityResource<Collection?, String?> {
        @MethodProperties(path = "api")
        override fun list(page: Page?, sort: Sort?): MutableList<Collection?>?

        @MethodProperties(path = "api")
        override fun get(name: String?): Collection?

        @MethodProperties(path = "api")
        override fun add(collection: Collection?): Collection?

        @MethodProperties(path = "api")
        override fun update(name: String?, collection: Collection?): Collection?

        @MethodProperties(path = "api")
        override fun delete(name: String?): Boolean
    }
}
