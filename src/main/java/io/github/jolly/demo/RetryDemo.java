package io.github.jolly.demo;

import io.github.jolly.retry.RetryPolicy;
import io.github.jolly.retry.RetryPolicyBuilder;

public class RetryDemo {
    public static void main(String[] args) {
        RetryPolicy pol = new RetryPolicyBuilder()
                .attempts(3)
                .waitDuration(500)
                .build();
        BackendService service = new CounterService();
        String result = (String) pol.exec(service::failForever);
        System.out.println(result);
    }
}
