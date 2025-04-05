package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheEntityResource
import io.quarkus.rest.data.panache.kotlin.ResourceProperties
import jakarta.transaction.Transactional
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import java.util.*

@ResourceProperties(hal = true, paged = false, halCollectionName = "item-collections")
interface CollectionsResource : PanacheEntityResource<Collection?, String?> {
    @GET
    @Path("/name/{name}")
    fun findByName(@PathParam("name") name: String?): Collection? {
        val collections: List<Collection?> = Collection.find("name = :name", Collections.singletonMap("name", name)).list()
        if (collections.isEmpty()) {
            return null
        }

        return collections.get(0)
    }

    @Transactional
    @POST
    @Path("/name/{name}")
    fun addByName(@PathParam("name") name: String?): Collection {
        val collection = Collection()
        collection.id = name
        collection.name = name
        collection.persist()
        return collection
    }
}
