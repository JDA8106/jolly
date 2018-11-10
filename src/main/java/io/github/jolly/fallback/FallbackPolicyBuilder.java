package io.github.jolly.fallback;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class FallbackPolicyBuilder<T> {

    private Supplier<T> userFallback;

    /**
     * Initializes FallbackPolicyBuilder
     */
    public FallbackPolicyBuilder(Supplier<T> userFallback) {
        this.userFallback = userFallback;
    }

    /**
     * Builds and returns FallbackPolicy
     * @return FallbackPolicy
     */
    public FallbackPolicy<T> build() {

        return new FallbackPolicy<T>(userFallback);
    }
}
