package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class EmptyListItem : PanacheEntityBase {
    @Id
    @GeneratedValue
    private val cid: Long? = null

    var name: String? = null

    @ManyToOne(optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var collection: Collection? = null
}
