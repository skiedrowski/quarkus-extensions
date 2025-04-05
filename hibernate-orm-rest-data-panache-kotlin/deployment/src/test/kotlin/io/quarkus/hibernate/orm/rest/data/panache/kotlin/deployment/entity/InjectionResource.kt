package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/call/resource")
class InjectionResource {
    @Inject
    lateinit var itemsResource: ItemsResource

    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    fun items(): MutableList<Item?>? {
        return itemsResource!!.list(Page(5), Sort.by("id"))
    }
}
