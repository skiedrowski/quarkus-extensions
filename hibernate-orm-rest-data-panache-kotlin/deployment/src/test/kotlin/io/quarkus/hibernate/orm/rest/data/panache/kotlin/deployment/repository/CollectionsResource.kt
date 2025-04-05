package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheRepositoryResource
import io.quarkus.rest.data.panache.kotlin.ResourceProperties

@ResourceProperties(hal = true, paged = false, halCollectionName = "item-collections")
interface CollectionsResource : PanacheRepositoryResource<CollectionsRepository, Collection, String>
