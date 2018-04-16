package io.github.jolly.retry;

public class CounterService implements BackendService {

    private static int counter = 0;

    public String doSomething() {
        if (counter == 0) {
            String temp = null;
            String bad = temp.toLowerCase();
            return "Bad";
        } else {
            counter++;
            return "Finally worked";
        }
    }
}
