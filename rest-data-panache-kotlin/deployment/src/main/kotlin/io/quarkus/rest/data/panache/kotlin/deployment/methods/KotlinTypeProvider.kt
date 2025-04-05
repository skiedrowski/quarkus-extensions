package io.quarkus.rest.data.panache.kotlin.deployment.methods

/** Experiment ... TODO see if really needed, hopefully removable/convertable to object ... */
class KotlinTypeProvider {

    fun ktLong(): Class<*> {
        return kotlin.Long::class.java
    }
}