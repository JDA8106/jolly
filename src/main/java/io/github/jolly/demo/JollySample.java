package io.github.jolly.demo;

import io.github.jolly.circuitbreaker.CircuitBreakerPolicy;
import io.github.jolly.circuitbreaker.CircuitBreakerPolicyBuilder;
import io.github.jolly.fallback.FallbackPolicy;
import io.github.jolly.fallback.FallbackPolicyBuilder;
import io.github.jolly.timeout.TimeoutPolicy;
import io.github.jolly.timeout.TimeoutPolicyBuilder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class JollySample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //testCircuitBreaker();
        //testTimeout();
        testFallbackWithBadFallbackFunction();
        testFallbackWithGoodFallbackFunction();



    }

    public static void testTimeout() throws ExecutionException, InterruptedException {
        BackendService backendService = new CounterService();
        TimeoutPolicy pol = new TimeoutPolicyBuilder().build();

        CompletableFuture<String> resFuture = pol.runAsync(backendService::goForever);

        String result = pol.exec(backendService::alwaysWork);
        System.out.println("Sync: " + result);

        System.out.println("Async: " + resFuture.get());

    }

    public static void testCircuitBreaker() throws InterruptedException {
        BackendService backendService = new CounterService();
        CircuitBreakerPolicy pol = new CircuitBreakerPolicyBuilder().build();
        try {
            String result = pol.exec(backendService::doSomething);
            System.out.println(result);
        } catch(NullPointerException e) {
            System.out.println("first");
        }

        try {
            String result = pol.exec(backendService::doSomething);
            System.out.println(result);
        } catch(CircuitBreakerOpenException e) {
            System.out.println("second");
        }

        Thread.sleep(1000);

        String result = pol.exec(backendService::doSomething);
        System.out.println(result);

        result = pol.exec(backendService::doSomething);
        System.out.println(result);

        result = pol.exec(backendService::doSomething);
        System.out.println(result);

        result = pol.exec(backendService::doSomething);
        System.out.println(result);
    }

    public static void testFallbackWithBadFallbackFunction() {
        // This sample is the case when the user supplied function to be executed has an exception,
        // and the user supplied fallback function ALSO has an exception, in which case,
        // the policy indicates that there is an "Exception in user supplied fallback function, calling default fallback function"
        // and it resorts to calling its default fallback function

        BackendService backendService = new CounterService();

        FallbackPolicy pol = new FallbackPolicyBuilder(backendService::runtimeExceptionFail).build();

        String result = pol.exec(backendService::runtimeExceptionFail);
        System.out.println("Sync: " + result);
    }

    public static void testFallbackWithGoodFallbackFunction() {
        // This sample is the case when the user supplied function to be executed has an exception,
        // but the user supplied fallback function does NOT have an exception

        BackendService backendService = new CounterService();

        FallbackPolicy pol = new FallbackPolicyBuilder(backendService::alwaysWork).build();

        String result = pol.exec(backendService::runtimeExceptionFail);
        System.out.println("Sync: " + result);
    }
}
