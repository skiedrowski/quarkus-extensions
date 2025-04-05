package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.openapi

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheRepositoryResource
import io.quarkus.rest.data.panache.kotlin.MethodProperties
import io.quarkus.rest.data.panache.kotlin.ResourceProperties
import jakarta.annotation.security.RolesAllowed

@ResourceProperties(hal = true, paged = false, halCollectionName = "item-collections", rolesAllowed = ["user"])
interface CollectionsResource : PanacheRepositoryResource<CollectionsRepository, Collection, String> {
    @RolesAllowed("superuser")
    override fun update(id: String?, entity: Collection?): Collection?

    @MethodProperties(rolesAllowed = ["admin"])
    override fun delete(name: String?): Boolean
}
