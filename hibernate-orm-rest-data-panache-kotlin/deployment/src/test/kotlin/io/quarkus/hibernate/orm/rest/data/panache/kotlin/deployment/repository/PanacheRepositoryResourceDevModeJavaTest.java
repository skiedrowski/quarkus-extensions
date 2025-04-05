package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository;

import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.AbstractDevModeJavaTest;
import io.quarkus.test.QuarkusDevModeTest;

/**
 * try to figure out if the test behaves the same when run implemented using java
 */
public class PanacheRepositoryResourceDevModeJavaTest extends AbstractDevModeJavaTest {

    @RegisterExtension
    static final QuarkusDevModeTest TEST = new QuarkusDevModeTest()
            .withApplicationRoot((jar) -> jar
                    .addClasses(Collection.class, AbstractEntity.class, AbstractItem.class, Item.class,
                            ItemsResource.class, ItemsRepository.class)
                    .addAsResource("application.properties")
                    .addAsResource("import.sql"));

}
