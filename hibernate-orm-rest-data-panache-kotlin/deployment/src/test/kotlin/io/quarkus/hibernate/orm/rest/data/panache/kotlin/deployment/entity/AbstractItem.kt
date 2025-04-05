package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.ManyToOne
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractItem<IdType : Number?> : AbstractEntity<IdType?>() {
    var name: String? = null

    @ManyToOne(optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var collection: Collection? = null
}
