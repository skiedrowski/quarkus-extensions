package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.build

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
open class Collection : PanacheEntityBase {
    companion object : PanacheCompanionBase<Collection, String> {}

    @Id
    open var id: String? = null

    lateinit var name: String
}
