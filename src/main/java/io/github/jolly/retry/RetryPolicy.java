package io.github.jolly.retry;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.function.Supplier;

public class RetryPolicy {

    public static <T> T exec(int attempts, int duration, Supplier<T> function) {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(attempts)
                .waitDuration(Duration.ofMillis(duration))
                .build();
        Retry retry = Retry.of("backendName", config);

        Supplier<T> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, function);

        decoratedSupplier = Retry
                .decorateSupplier(retry, decoratedSupplier);

        // Execute the decorated supplier and recover from any exception
        T result = Try.ofSupplier(decoratedSupplier).get();

        return result;
    }


}
