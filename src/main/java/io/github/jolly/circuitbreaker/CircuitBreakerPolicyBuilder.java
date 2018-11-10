package io.github.jolly.circuitbreaker;

public class CircuitBreakerPolicyBuilder<T> {

/**
 * The CircuitBreakerPolicyBuilder class generates a CircuitBreakerPolicy to be reused
 * for method invocation with a CircuitBreakerPolicy.
 */
    private int rateThreshold = 100;
    private int duration = 1000;
    private int sizeRingBufferHalfOpen = 2;
    private int sizeRingBufferClosed = 2;

    /**
     * Sets the failure rate threshold in percentage above which the CircuitBreaker should trip open
     * and start short-circuiting calls
     * @param rateThreshold rate threshold
     * @return builder
     */
    public CircuitBreakerPolicyBuilder rateThreshold(int rateThreshold) {
        this.rateThreshold = rateThreshold;
        return this;
    }

    /**
     * Sets wait duration between attempts
     * @param duration wait duration in milliseconds
     * @return builder
     */
    public CircuitBreakerPolicyBuilder waitDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the size of the ring buffer when the CircuitBreaker is half open
     * @param sizeRingBufferHalfOpen the size of the ring buffer when the CircuitBreaker is half open
     * @return builder
     */
    public CircuitBreakerPolicyBuilder sizeRingBufferHalfOpen(int sizeRingBufferHalfOpen) {
        this.sizeRingBufferHalfOpen = sizeRingBufferHalfOpen;
        return this;
    }

    /**
     * Sets the size of the ring buffer when the CircuitBreaker is Closed
     * @param sizeRingBufferClosed the size of the ring buffer when the CircuitBreaker is Closed
     * @return builder
     */
    public CircuitBreakerPolicyBuilder sizeRingBufferClosed(int sizeRingBufferClosed) {
        this.sizeRingBufferClosed = sizeRingBufferClosed;
        return this;
    }

    /**
     * Builds and returns CircuitBreakerPolicy with requested parameters.
     * @return CircuitBreakerPolicy with config parameters
     */
    public CircuitBreakerPolicy<T> build() {
        return new CircuitBreakerPolicy<T>(rateThreshold, duration, sizeRingBufferHalfOpen, sizeRingBufferClosed);
    }
}
