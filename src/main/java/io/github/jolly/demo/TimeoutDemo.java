package io.github.jolly.demo;

import io.github.jolly.timeout.TimeoutPolicy;
import io.github.jolly.timeout.TimeoutPolicyBuilder;

import java.util.concurrent.CompletableFuture;

public class TimeoutDemo {
    public static void main(String[] args) {
        BackendService backendService = new CounterService();
        TimeoutPolicy<String> pol = new TimeoutPolicyBuilder<String>().build();

        CompletableFuture<String> resFuture = pol.runAsync(backendService::goForever);

        String result = pol.exec(backendService::alwaysWork);
        System.out.println(result);
    }
}
