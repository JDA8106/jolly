package io.github.jolly.demo;

public class CounterService implements BackendService {

    private static int counter = 0;

    public String doSomething() {
        if (counter == 0) {
            counter++;
            String temp = null;
            System.out.println("Failed");
            String bad = temp.toLowerCase();
            return "Bad";
        } else {
            counter++;
            return "Finally worked";
        }
    }

    public String failForever() {
        if (counter == 0) {
            String temp = null;
            System.out.println("Failed");
            String bad = temp.toLowerCase();
            return "Bad";
        } else {
            counter++;
            return "Finally worked";
        }
    }
}
