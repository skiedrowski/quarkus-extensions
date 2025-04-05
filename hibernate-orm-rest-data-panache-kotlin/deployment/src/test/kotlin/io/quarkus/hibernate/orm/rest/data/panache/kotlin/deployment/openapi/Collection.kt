package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.openapi

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.*

@Entity
class Collection {
    @Id
    var id: String? = null

    var name: String? = null

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "collection")
    var items: MutableList<Item?>? = LinkedList<Item?>()
}
