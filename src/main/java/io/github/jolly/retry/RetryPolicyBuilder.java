package io.github.jolly.retry;

/**
 * The RetryPolicyBuilder class generates a RetryPolicy to be reused
 * for method invocation with a retry policy.
 */
public class RetryPolicyBuilder {
    private int attempts = 3;
    private int duration = 500;

    /**
     * Sets max attempts to try before failure
     * @param attempts max attempts
     * @return builder
     */
    public RetryPolicyBuilder attempts(int attempts) {
        this.attempts = attempts;
        return this;
    }

    /**
     * Sets wait duration between attempts
     * @param duration wait duration in milliseconds
     * @return builder
     */
    public RetryPolicyBuilder waitDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Builds and returns RetryPolicy with requested paramters.
     * @return RetryPolicy with config parameters
     */
    public RetryPolicy build() {
        return new RetryPolicy(attempts, duration);
    }
}
