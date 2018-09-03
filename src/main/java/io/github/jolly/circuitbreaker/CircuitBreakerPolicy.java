package io.github.jolly.circuitbreaker;
import io.github.jolly.policy.Policy;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CircuitBreakerPolicy extends Policy {

    private int rateThreshold;
    private int duration;
    private int sizeRingBufferHalfOpen;
    private int sizeRingBufferClosed;

    /**
     * Initializes CircuitBreakerPolicy with max attempts and wait duration between attempts.
     * @param rateThreshold the failure rate threshold in percentage above which the CircuitBreaker should trip open
     *                     and start short-circuiting calls
     * @param duration wait duration between attempts
     * @param sizeRingBufferHalfOpen the size of the ring buffer when the CircuitBreaker is half open
     * @param sizeRingBufferClosed the size of the ring buffer when the CircuitBreaker is closed
     */
    public CircuitBreakerPolicy(int rateThreshold, int duration, int sizeRingBufferHalfOpen, int sizeRingBufferClosed) {
        this.rateThreshold = rateThreshold;
        this.duration = duration;
        this.sizeRingBufferHalfOpen = sizeRingBufferHalfOpen;
        this.sizeRingBufferClosed = sizeRingBufferClosed;
    }

    /**
     * Executes method with the given parameters rateThreshold, duration, sizeRingBufferHalfOpen, sizeRingBufferClosed)
     * @param function function to be executed with the CircuitBreakerPolicy
     * @param <T>
     * @return output of method on success or exception on failure
     */
    public <T> T exec(Supplier<T> function) {

        // Custom configuration for a CircuitBreaker with default values of ringBufferSizes
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(rateThreshold)
                .waitDurationInOpenState(Duration.ofMillis(duration))
                .ringBufferSizeInHalfOpenState(sizeRingBufferHalfOpen)
                .ringBufferSizeInClosedState(sizeRingBufferClosed)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("default", circuitBreakerConfig);

        Supplier<T> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, function);

        decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, decoratedSupplier);

        T result = Try.ofSupplier(decoratedSupplier)
                .get();

        return result;
    }
}
