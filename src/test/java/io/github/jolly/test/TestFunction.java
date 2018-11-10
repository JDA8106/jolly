package io.github.jolly.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Provides a function to test resilience patterns with.
 *
 * @author Arda Pekis
 * @version 1.0
 */
class TestFunction {
    private final int delay; // Delay in milliseconds
    private final List<Boolean> sequence; // Sequence of successes / failures
    private final long start;
    private int index = 0;

    TestFunction(List<Boolean> sequence) {
        this(sequence, 0);
    }

    TestFunction(List<Boolean> sequence, int delay) {
        this.sequence = sequence;
        this.delay = delay;
        this.start = System.currentTimeMillis();
    }

    /**
     * Waits delay milliseconds, then returns the elapsed time from
     * its construction or a Runtime error, based on the sequence.
     * @return elapsed time from construction
     */
    Long func() {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (sequence.get(index % sequence.size())) {
            index++;
            return System.currentTimeMillis() - start;
        }
        else {
            index++;
            throw new TestException("TestFunction failed on run " + Integer.toString(index));
        }
    }
}
