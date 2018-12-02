package io.github.jolly.demo;

public interface BackendService {
    String doSomething();
    String failForever();
    String goForever();
    String alwaysWork();
    String runtimeExceptionFail();
    String fallbackFail();
}
