package io.github.jolly.fallback;

import java.util.concurrent.Supplier;

public class FallbackPolicyBuilder {

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
    public FallbackPolicy build() {

        return new FallbackPolicy(userFallback);
    }
}
