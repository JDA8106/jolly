package io.github.jolly.retry;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Try;

import java.util.function.Supplier;

public class RetryPolicy {
    public static void main(String[] args) {
        // Create a CircuitBreaker (use default configuration)
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");
        // Create a RetryPolicy with at most 3 retries and a fixed time interval between retries of 500ms
        Retry retry = Retry.ofDefaults("backendName");

        // Decorate your call to BackendService.doSomething() with a CircuitBreaker
        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, RetryPolicy::doSomething);

        // Decorate your call with automatic retry
        decoratedSupplier = Retry
                .decorateSupplier(retry, decoratedSupplier);

        // Execute the decorated supplier and recover from any exception
        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();
    }

    public static String doSomething() {
        return "Nothing";
    }
}
