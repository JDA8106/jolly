package io.github.jolly.policy;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Abstract Policy that is superclass of all policies in Jolly.
 */
public abstract class Policy {

    public abstract <K, V> V exec(Supplier<K> function);

    /**
     * Executes method asynchronously with given parameters
     * @param function function to be executed with the Policy
     * @return CompletableFuture of output of method on success or exception on failure
     */
    public <K, V> CompletableFuture<V> runAsync(Supplier<K> function) {
        return CompletableFuture.supplyAsync(() -> exec(function));
    }
}
