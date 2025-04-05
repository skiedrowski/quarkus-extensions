package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.PanacheEntityResource
import io.quarkus.rest.data.panache.kotlin.ResourceProperties

/**
 * Having a path param in the path reproduces the issue of having HAL enabled spites it should be disabled by default.
 */
@ResourceProperties(path = "/{group}/projects")
interface ProjectResource : PanacheEntityResource<Project?, String?>
