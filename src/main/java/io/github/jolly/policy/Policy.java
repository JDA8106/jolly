package io.github.jolly.policy;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Abstract Policy that is superclass of all policies in Jolly.
 */
public abstract class Policy {

    public abstract <T> T exec(Supplier<T> function);

    /**
     * Executes method asynchronously with given parameters
     * @param function function to be executed with the Policy
     * @param <T>
     * @return CompletableFuture of output of method on success or exception on failure
     */
    public <T> CompletableFuture<T> runAsync(Supplier<T> function) {
        return CompletableFuture.supplyAsync(() -> exec(function));
    }
}
