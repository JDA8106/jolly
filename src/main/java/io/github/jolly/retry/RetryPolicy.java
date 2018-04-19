package io.github.jolly.retry;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * The RetryPolicy class executes methods and re-attempts execution when
 * method fails for a certain number of attempts.
 */
public class RetryPolicy {
    private int attempts;
    private int duration;

    /**
     * Initializes RetryPolicy with max attempts and wait duration between attempts.
     * @param attempts max number of attempts to try before failing
     * @param duration wait duration between attempts
     */
    public RetryPolicy(int attempts, int duration) {
        this.attempts = attempts;
        this.duration = duration;
    }


    /**
     * Executes method with the given parameters (attempts, duration)
     * @param function function to be executed with the RetryPolicy
     * @param <T>
     * @return output of method on success or exception on failure
     */
    public <T> T exec(Supplier<T> function) {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(this.attempts)
                .waitDuration(Duration.ofMillis(this.duration))
                .build();
        Retry retry = Retry.of("backendName", config);

        Supplier<T> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, function);

        decoratedSupplier = Retry
                .decorateSupplier(retry, decoratedSupplier);

        T result = Try.ofSupplier(decoratedSupplier)
                .get();

        return result;
    }


}
