package io.github.jolly.demo;

import io.github.jolly.circuitbreaker.CircuitBreakerPolicy;
import io.github.jolly.circuitbreaker.CircuitBreakerPolicyBuilder;
import io.github.jolly.timeout.TimeoutPolicy;
import io.github.jolly.timeout.TimeoutPolicyBuilder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;

public class JollySample {
    public static void main(String[] args) throws InterruptedException {

        //testCircuitBreaker();
        testTimeout();

//        BackendService backendService = new CounterService();
//        RetryPolicy pol = new RetryPolicyBuilder()
//                .attempts(3)
//                .waitDuration(1000)
//                .build();
//
//        System.out.println("Before Async");
//        CompletableFuture<String> result1 = pol.runAsync(backendService::failForever);
//
//        System.out.println("After Async");
//        try {
//            //String result = pol.exec(backendService::doSomething);
//            String result = pol.exec(backendService::failForever);
//        } catch (Exception e) {
//            System.out.println("Sync failed with: " + e.getMessage());
//        }
//        System.out.println("After Sync");
//
//        try {
//            System.out.println(result1.get());
//        } catch (Exception e) {
//            System.out.println("Async failed with: " + e.getMessage());
//        }
//        System.out.println("After Async");
//
//        BackendService backendService2 = new CounterService();
//
//        RetryPolicy pol2 = new RetryPolicyBuilder()
//
//                .attempts(3)
//
//                .waitDuration(500)
//
//                .build();
//
//
//
//        //String result = pol2.exec(backendService2::doSomething);
//
//
//
//        System.out.println("dfsfd");
//
//        String result = pol2.exec(backendService2::failForever);
//
//
//
//        System.out.println(result);


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

    public static void testTimeout() throws InterruptedException {
        BackendService backendService = new CounterService();
        TimeoutPolicy pol = new TimeoutPolicyBuilder().build();
        try {
            String result = pol.exec(backendService::goForever);
            System.out.println(result);
        } catch(Exception e) {
            System.out.println("exception");
        }
    }
}
