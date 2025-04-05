package io.quarkus.hibernate.orm.rest.data.panache.kotlin;

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase;
import io.quarkus.rest.data.panache.kotlin.MethodProperties;
import io.quarkus.rest.data.panache.kotlin.ResourceProperties;
import io.quarkus.rest.data.panache.kotlin.RestDataResource;

/**
 * REST data Panache resource that uses {@link PanacheRepositoryBase} instance for data access and exposes it as a JAX-RS
 * resource.
 * <p>
 * See {@link RestDataResource} for the methods provided by this resource.
 * <p>
 * See {@link ResourceProperties} and {@link MethodProperties} for the ways to customize this resource.
 *
 * @param <Repository> {@link PanacheRepositoryBase} instance that should be used for data access.
 * @param <Entity> Entity type that is handled by this resource and the linked {@link PanacheRepositoryBase} instance.
 * @param <ID> ID type of the entity.
 */
public interface PanacheRepositoryResource<Repository extends PanacheRepositoryBase<Entity, ID>, Entity, ID>
        extends RestDataResource<Entity, ID> {

}
