package io.quarkus.rest.data.panache.kotlin.deployment;

import io.quarkus.builder.item.MultiBuildItem;
import io.quarkus.rest.data.panache.kotlin.deployment.ResourceMetadata;

public final class RestDataResourceBuildItem extends MultiBuildItem {

    private final ResourceMetadata resourceMetadata;

    public RestDataResourceBuildItem(ResourceMetadata resourceMetadata) {
        this.resourceMetadata = resourceMetadata;
    }

    public ResourceMetadata getResourceMetadata() {
        return resourceMetadata;
    }
}
