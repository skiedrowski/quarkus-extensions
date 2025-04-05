package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment.entity

import io.quarkus.hibernate.orm.rest.data.panache.kotlin.RestDataResourceMethodListener
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ItemRestDataResourceMethodListener : RestDataResourceMethodListener<AbstractItem<*>?> {
    override fun onBeforeAdd(item: AbstractItem<*>?) {
        PanacheEntityResourceMethodListenerTest.ON_BEFORE_SAVE_COUNTER.incrementAndGet()
    }

    override fun onAfterAdd(item: AbstractItem<*>?) {
        PanacheEntityResourceMethodListenerTest.ON_AFTER_SAVE_COUNTER.incrementAndGet()
    }

    override fun onBeforeUpdate(item: AbstractItem<*>?) {
        PanacheEntityResourceMethodListenerTest.ON_BEFORE_UPDATE_COUNTER.incrementAndGet()
    }

    override fun onAfterUpdate(item: AbstractItem<*>?) {
        PanacheEntityResourceMethodListenerTest.ON_AFTER_UPDATE_COUNTER.incrementAndGet()
    }

    override fun onBeforeDelete(id: Any?) {
        PanacheEntityResourceMethodListenerTest.ON_BEFORE_DELETE_COUNTER.incrementAndGet()
    }

    override fun onAfterDelete(id: Any?) {
        PanacheEntityResourceMethodListenerTest.ON_AFTER_DELETE_COUNTER.incrementAndGet()
    }
}
