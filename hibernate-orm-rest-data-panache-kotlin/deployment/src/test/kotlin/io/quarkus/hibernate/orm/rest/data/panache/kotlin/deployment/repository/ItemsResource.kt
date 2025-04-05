package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.repository

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheRepositoryResource
import io.quarkus.rest.data.panache.kotlin.ResourceProperties

@ResourceProperties(hal = true)
interface ItemsResource : PanacheRepositoryResource<ItemsRepository, Item, Long>
