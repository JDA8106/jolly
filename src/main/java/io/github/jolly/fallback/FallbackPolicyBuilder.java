package io.github.jolly.fallback;

import java.util.concurrent.Callable;

public class FallbackPolicyBuilder {

    private Callable userFallback;

    /**
     * Initializes FallbackPolicyBuilder
     */
    public FallbackPolicyBuilder(Callable userFallback) {
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
