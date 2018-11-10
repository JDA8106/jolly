package io.github.jolly.timeout;

/**
 * The TimeoutPolicyBuilder class generates a TimeoutPolicy to be reused
 * for method invocation with a TimeoutPolicy.
 */
public class TimeoutPolicyBuilder<T> {

    private int duration = 5000;
    private boolean cancelFuture = false;

    /**
     * Sets wait duration before timeout occurs
     * @param duration duration in milliseconds
     * @return builder
     */
    public TimeoutPolicyBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets cancelFuture, if true, an attempt will be made to cancel the future if cancelFuture is true.
     * @param cancelFuture whether future should be cancelled
     * @return builder
     */
    public TimeoutPolicyBuilder cancelFuture(boolean cancelFuture) {
        this.cancelFuture = cancelFuture;
        return this;
    }

    /**
     * Builds and returns CircuitBreakerPolicy with requested parameters.
     * @return CircuitBreakerPolicy with config parameters
     */
    public TimeoutPolicy<T> build() {
        return new TimeoutPolicy<T>(duration, cancelFuture);
    }
}
