package io.github.jolly.demo;
import java.util.concurrent.CompletableFuture;

import io.github.jolly.retry.RetryPolicy;
import io.github.jolly.retry.RetryPolicyBuilder;

public class JollySample {
    public static void main(String[] args) {

        BackendService backendService = new CounterService();
        RetryPolicy pol = new RetryPolicyBuilder()
                .attempts(3)
                .waitDuration(1000)
                .build();

        System.out.println("Before Async");
        CompletableFuture<String> result1 = pol.run(backendService::failForever);

        System.out.println("After Async");
        try {
            String result = pol.exec(backendService::doSomething);
            //String result = pol.exec(backendService::failForever);
        } catch (Exception e) {
            System.out.println("Sync failed with: " + e.getMessage());
        }
        System.out.println("After Sync");

        try {
            System.out.println(result1.get());
        } catch (Exception e) {
            System.out.println("Async failed with: " + e.getMessage());
        }
        System.out.println("After Async");

        BackendService backendService2 = new CounterService();

        RetryPolicy pol2 = new RetryPolicyBuilder()

                .attempts(3)

                .waitDuration(500)

                .build();



        String result = pol2.exec(backendService2::doSomething);



        System.out.println("dfsfd");

        //String result = pol2.exec(backendService2::failForever);



        System.out.println(result);
    }
}
