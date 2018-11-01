package io.github.jolly.fallback;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class FallbackPolicyBuilder {

    private Supplier userFallback;

    /**
     * Initializes FallbackPolicyBuilder
     */
    public FallbackPolicyBuilder(Supplier userFallback) {
        this.userFallback = userFallback;
    }

    /**
     * Builds and returns FallbackPolicy
     * @return FallbackPolicy
     */
    public FallbackPolicy build() {

        return new FallbackPolicy(userFallback);
    }
}
