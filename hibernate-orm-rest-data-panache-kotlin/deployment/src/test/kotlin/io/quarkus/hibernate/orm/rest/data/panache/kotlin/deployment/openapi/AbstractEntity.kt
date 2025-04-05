package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.openapi

import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity<IdType : Number?> {
    @Id
    @GeneratedValue
    val id: IdType? = null
}
