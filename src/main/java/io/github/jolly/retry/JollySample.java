package io.github.jolly.retry;

public class JollySample {
    public static void main(String[] args) {
        BackendService backendService = new CounterService();
        System.out.println(RetryPolicy.exec(3, 500, backendService::doSomething));
    }
}
