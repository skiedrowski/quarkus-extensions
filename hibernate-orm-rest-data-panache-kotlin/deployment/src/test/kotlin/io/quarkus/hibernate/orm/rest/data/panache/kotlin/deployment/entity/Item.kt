package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import jakarta.persistence.Entity
import jakarta.persistence.NamedQuery

@Entity
@NamedQuery(name = "Item.containsInName", query = "from Item where name like CONCAT('%', CONCAT(:name, '%'))")
class Item : AbstractItem<Long?>()
