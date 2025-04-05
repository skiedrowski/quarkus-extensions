package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheEntityResource
import io.quarkus.rest.data.panache.kotlin.ResourceProperties

@ResourceProperties(hal = true)
interface ItemsResource : PanacheEntityResource<Item?, Long?>
