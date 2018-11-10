package io.github.jolly.circuitbreaker;
import io.github.jolly.policy.Policy;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import java.time.Duration;
import java.util.function.Supplier;

/**
 * TimeoutPolicy class
 * @author Anish Visaria, Geetika Kapoor
 */
public class CircuitBreakerPolicy<T> extends Policy<T> {
    private CircuitBreaker circuitBreaker;
    private int duration;

    /**
     * Initializes CircuitBreakerPolicy with max attempts and wait duration between attempts.
     * @param rateThreshold the failure rate threshold in percentage above which the CircuitBreaker should trip open
     *                     and start short-circuiting calls
     * @param duration wait duration between attempts
     * @param sizeRingBufferHalfOpen the size of the ring buffer when the CircuitBreaker is half open
     * @param sizeRingBufferClosed the size of the ring buffer when the CircuitBreaker is closed
     */
    public CircuitBreakerPolicy(int rateThreshold, int duration, int sizeRingBufferHalfOpen, int sizeRingBufferClosed) {

        this.duration = duration;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(rateThreshold)
                .waitDurationInOpenState(Duration.ofMillis(duration))
                .ringBufferSizeInHalfOpenState(sizeRingBufferHalfOpen)
                .ringBufferSizeInClosedState(sizeRingBufferClosed)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("default", circuitBreakerConfig);
    }

    /**
     * Executes method with the given parameters rateThreshold, duration, sizeRingBufferHalfOpen, sizeRingBufferClosed)
     * @param function function to be executed with the CircuitBreakerPolicy
     * @return output of method on success or exception on failure
     */
    public T exec(Supplier<T> function) {
        Supplier<T> decoratedSupplier = CircuitBreaker.decorateSupplier(this.circuitBreaker, function);

        System.out.println(circuitBreaker.getState());

        Try<T> result = Try.ofSupplier(decoratedSupplier);
        if (circuitBreaker.getState().equals(CircuitBreaker.State.OPEN)) {
            throw new CircuitBreakerOpenException("Cannot execute method: must wait " + duration);
        } else {
            return result.get();
        }
    }
}
