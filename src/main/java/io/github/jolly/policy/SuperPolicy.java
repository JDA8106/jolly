package io.github.jolly.policy;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class SuperPolicy<K, V> {
    public abstract V exec(Supplier<K> function);

    /**
     * Executes method asynchronously with given parameters
     * @param function function to be executed with the Policy
     * @return CompletableFuture of output of method on success or exception on failure
     */
    public CompletableFuture<V> runAsync(Supplier<K> function) {
        return CompletableFuture.supplyAsync(() -> exec(function));
    }
}
