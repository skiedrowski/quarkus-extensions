package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.build

import io.quarkus.arc.properties.IfBuildProperty
import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheEntityResource

@IfBuildProperty(name = "collections.enabled", stringValue = "true")
interface CollectionsResource : PanacheEntityResource<Collection, String>
