package io.github.jolly.test;

/**
 * Exception to indicate failures for the purpose of testing.
 *
 * @author Arda Pekis
 * @version 1.0
 */
class TestException extends RuntimeException {
    TestException(String message) {
        super(message);
    }

    TestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
