package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.util.*

@Entity
class Collection : PanacheEntityBase {
    companion object CollectionO : PanacheCompanionBase<Collection, String> {}
    @JvmField
    @Id
    var id: String? = null

    @JvmField
    var name: String? = null

    /**
     * This field is used to reproduce the issue: https://github.com/quarkusio/quarkus/issues/30605
     */
    @Column(name = "type", columnDefinition = "int default 100")
    var type: Int = 0

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "collection")
    var items: MutableList<Item?> = LinkedList<Item?>()
}
