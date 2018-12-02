package io.github.jolly.demo;

public class CounterService implements BackendService {

    private static int counter = 0;

    public String doSomething() {
        if (counter < 2) {
            counter++;
            String temp = null;
            System.out.println("Failed");
            String bad = temp.toLowerCase();
            return "Bad";
        } else {
            System.out.println("Working");
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
            System.out.println("Working");
            counter++;
            return "Finally worked";
        }
    }

    public String goForever() {
        while (true);
    }

    public String alwaysWork() {
        return "This code keeps failing.";
    }

    public String fallbackFail() {
        return "The executed code failed. Now running the user-defined fallback code.";
    }

    public String runtimeExceptionFail() {
        throw new RuntimeException("Code failed due to some error. Let's say this error occurred because the website the user" +
                "is trying to access is unresponsive.");
    }
}
