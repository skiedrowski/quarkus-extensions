package io.quarkus.hibernate.orm.rest.data.panache.kotlin;

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase;
import io.quarkus.rest.data.panache.kotlin.MethodProperties;
import io.quarkus.rest.data.panache.kotlin.ResourceProperties;
import io.quarkus.rest.data.panache.kotlin.RestDataResource;

/**
 * REST data Panache resource that uses {@link PanacheEntityBase} instance for data access and exposes it as a JAX-RS resource.
 * <p>
 * See {@link RestDataResource} for the methods provided by this resource.
 * <p>
 * See {@link ResourceProperties} and {@link MethodProperties} for the ways to customize this resource.
 *
 * @param <Entity> {@link PanacheEntityBase} that is handled by this resource.
 * @param <ID> ID type of the entity.
 */
public interface PanacheEntityResource<Entity extends PanacheEntityBase, ID> extends RestDataResource<Entity, ID> {

}
