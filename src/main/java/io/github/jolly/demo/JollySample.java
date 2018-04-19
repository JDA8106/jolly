package io.github.jolly.demo;
import io.github.jolly.retry.RetryPolicy;
import io.github.jolly.retry.RetryPolicyBuilder;

public class JollySample {
    public static void main(String[] args) {

        BackendService backendService = new CounterService();
        RetryPolicy pol = new RetryPolicyBuilder()
                .attempts(3)
                .waitDuration(500)
                .build();

        //String result = pol.exec(backendService::doSomething);
        String result = pol.exec(backendService::failForever);

        System.out.println(result);
    }
}
