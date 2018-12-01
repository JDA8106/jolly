package io.github.jolly.bulkhead;

import io.github.jolly.policy.Policy;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.vavr.control.Try;

import java.util.function.Supplier;

/**
 * BulkheadPolicy class allows certain max concurrent calls until function supplier thread is blocked.
 * @param <T>
 *
 * @author Anish Visaria
 */
public class BulkheadPolicy<T> extends Policy<T> {

    private Bulkhead bulkhead;

    /**
     * Initializes BulkheadPolicy with bulkhead with specified configurations
     * @param bulkhead
     */
    public BulkheadPolicy(Bulkhead bulkhead) {
        this.bulkhead = bulkhead;
    }


    @Override
    public T exec(Supplier<T> function) {
        Supplier<T> decoratedSupplier = Bulkhead.decorateSupplier(this.bulkhead, function);
        Try<T> result = Try.ofSupplier(decoratedSupplier);
        return result.get();
    }
}
