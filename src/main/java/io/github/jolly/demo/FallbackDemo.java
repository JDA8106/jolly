package io.github.jolly.demo;

import io.github.jolly.fallback.FallbackPolicy;
import io.github.jolly.fallback.FallbackPolicyBuilder;

public class FallbackDemo {
    public static void main(String[] args) {
        testFallbackWithBadFallbackFunction();
        //testFallbackWithGoodFallbackFunction();
    }

    public static void testFallbackWithBadFallbackFunction() {
        // This sample is the case when the user supplied function to be executed has an exception,
        // and the user supplied fallback function ALSO has an exception, in which case,
        // the policy indicates that there is an "Exception in user supplied fallback function, calling default fallback function"
        // and it resorts to calling its default fallback function

        BackendService backendService = new CounterService();

        FallbackPolicy<String> pol = new FallbackPolicyBuilder<String>(backendService::runtimeExceptionFail).build();
        try {
            String result = pol.exec(backendService::runtimeExceptionFail);
        } catch (RuntimeException e) {
            System.out.println();
        }

    }

    public static void testFallbackWithGoodFallbackFunction() {
        // This sample is the case when the user supplied function to be executed has an exception,
        // but the user supplied fallback function does NOT have an exception

        BackendService backendService = new CounterService();

        FallbackPolicy<String> pol = new FallbackPolicyBuilder<String>(backendService::fallbackFail).build();

        String result = pol.exec(backendService::runtimeExceptionFail);
        System.out.println(result);
    }
}
