package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.openapi

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CollectionsRepository : PanacheRepositoryBase<Collection, String>
