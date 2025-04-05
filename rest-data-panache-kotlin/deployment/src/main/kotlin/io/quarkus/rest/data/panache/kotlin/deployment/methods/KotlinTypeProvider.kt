package io.quarkus.rest.data.panache.kotlin.deployment.methods

/** Experiment ... TODO see if really needed, hopefully removable/convertable to object ... */
object KotlinTypeProvider {

    @JvmField
    val ktLong: Class<*> = kotlin.Long::class.java

    @JvmField
    val ktAny: Class<*> = kotlin.Any::class.java

    @JvmField
    val ktBoolean: Class<*> = kotlin.Boolean::class.java
}