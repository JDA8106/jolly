package io.github.jolly.fallback;

import io.github.jolly.policy.Policy;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * FallbackPolicy class
 * @author Geetika Kapoor
 */
public class FallbackPolicy<T> extends Policy<T> {

    private Supplier<T> userFallback;
    /**
     * Initializes FallbackPolicy
     */
    public FallbackPolicy(Supplier<T> userFallback) {
        this.userFallback = userFallback;
    }

    /**
     * Executes method with the option of fallback in case of an exception
     * @param exception passed in case of error/exception
     * @return output of method on success or exception on failure
     */
    private T fallback(Exception exception) {
        // Handle exception and invoke fallback
        return null;
    }

    /**
     * Executes method with the option of fallback in case of a Throwable
     * @param function function to be executed with the FallbackPolicy
     * @return output of method on success or exception on failure
     */
    public T exec(Supplier<T> function) {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("default");

        Supplier<T> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, function);

        try {
            return Try.ofSupplier(decoratedSupplier)
                    .get();
        } catch (Exception error) {
            error.printStackTrace();
            if (this.userFallback != null) {
                try {
                    return userFallback.get();
                }
                catch (Exception e) {
                    // If user supplied fallback function also has an exception
                    System.out.println("Exception (error) in the use-defined fallback code. Now calling the default fallback code.");
                    return fallback(error);
                }
            }
            else {
                return fallback(error);
            }
        }
    }

}
