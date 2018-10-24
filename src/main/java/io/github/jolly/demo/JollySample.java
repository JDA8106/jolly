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
//        testTimeout();
        testFallback();


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

    public static void testFallback() {
        BackendService backendService = new CounterService();

        FallbackPolicy pol = new FallbackPolicyBuilder().build();

        String result = pol.exec(backendService::runtimeExceptionFail);
        System.out.println("Sync: " + result);

    }
}
