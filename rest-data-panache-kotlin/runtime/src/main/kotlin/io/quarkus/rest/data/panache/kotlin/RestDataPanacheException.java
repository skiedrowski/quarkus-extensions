package io.quarkus.rest.data.panache.kotlin;

public class RestDataPanacheException extends RuntimeException {

    public RestDataPanacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
