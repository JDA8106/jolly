package io.github.jolly.bulkhead;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;

/**
 * Generates BulkheadPolicy with custom configuration.
 * @param <T>
 *
 * @author Anish Visaria
 */
public class BulkheadPolicyBuilder<T> {

    private int maxConcurrentCalls = 150;
    private int maxWaitTime = 100;

    /**
     * Sets max concurrent calls.
     * @param calls max concurrent calls
     * @return BulkheadPolicyBuilder instance
     */
    public BulkheadPolicyBuilder<T> maxConcurrentCalls(int calls) {
        this.maxConcurrentCalls = calls;
        return this;
    }

    /**
     * Sets max wait time
     * @param waitTime max wait time
     * @return BulkheadPolicyBuilder instance
     */
    public BulkheadPolicyBuilder<T> maxWaitTime(int waitTime) {
        this.maxWaitTime = waitTime;
        return this;
    }

    /**
     * Builds BulkheadPolicy with configurations
     * @return BulkheadPolicy instance
     */
    public BulkheadPolicy<T> build() {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(maxConcurrentCalls)
                .maxWaitTime(maxWaitTime)
                .build();
        Bulkhead bulkhead = Bulkhead.of("bulkhead", config);
        return new BulkheadPolicy<>(bulkhead);
    }
}
