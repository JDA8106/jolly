package io.github.jolly.test;

import junit.extensions.RepeatedTest;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.github.jolly.retry.RetryPolicy;
import io.github.jolly.retry.RetryPolicyBuilder;

import static org.junit.Assert.*;

/**
 * Test the retry policy.
 *
 * @author Arda Pekis
 * @version 1.0
 */
public class RetryPolicyTest {
    private static final int ATTEMPTS = 3;
    private static final int WAIT_DURATION = 10;
    private static final RetryPolicy policy = new RetryPolicyBuilder().attempts(ATTEMPTS).waitDuration(WAIT_DURATION).build();

    @Test
    public void SyncSucceedImmediately() {
        TestFunction func = new TestFunction(Arrays.asList(true));
        long elapsed = policy.exec(func::func);
        assertTrue(elapsed < 10);
    }

    @Test
    public void SyncSucceedAfterOneFailure() {
        TestFunction func = new TestFunction(Arrays.asList(false, true));
        long elapsed = policy.exec(func::func);
        assertFalse(elapsed < 10);
        assertTrue(elapsed < 20);
    }

    @Test
    public void SyncSucceedAfterTwoFailures() {
        TestFunction func = new TestFunction(Arrays.asList(false, false, true));
        long elapsed = policy.exec(func::func);
        assertFalse(elapsed < 20);
        assertTrue(elapsed < 30);
    }

    @Test(expected = TestException.class)
    public void SyncFailAfterThreeFailures() {
        TestFunction func = new TestFunction(Arrays.asList(false, false, false, true));
        long elapsed = policy.exec(func::func);
        fail("Should have thrown an exception");
    }

    @Test
    public void AsyncSucceedImmediately() throws ExecutionException, InterruptedException {
        TestFunction func = new TestFunction(Arrays.asList(true));
        CompletableFuture<Long> elapsed = policy.runAsync(func::func);
        TimeUnit.MILLISECONDS.sleep(1);
        assertTrue(elapsed.get() < 10);
    }

    @Test
    public void AsyncSucceedAfterOneFailure() throws ExecutionException, InterruptedException {
        TestFunction func = new TestFunction(Arrays.asList(false, true));
        CompletableFuture<Long> elapsed = policy.runAsync(func::func);
        TimeUnit.MILLISECONDS.sleep(12);
        assertFalse(elapsed.get() < 10);
        assertTrue(elapsed.get() < 20);
    }

    @Test
    public void AsyncSucceedAfterTwoFailures() throws ExecutionException, InterruptedException {
        TestFunction func = new TestFunction(Arrays.asList(false, false, true));
        CompletableFuture<Long> elapsed = policy.runAsync(func::func);
        TimeUnit.MILLISECONDS.sleep(22);
        assertFalse(elapsed.get() < 20);
        assertTrue(elapsed.get() < 30);
    }

    @Test(expected = ExecutionException.class)
    public void AsyncFailAfterThreeFailures() throws ExecutionException, InterruptedException {
        TestFunction func = new TestFunction(Arrays.asList(false, false, false, true));
        CompletableFuture<Long> elapsed = policy.runAsync(func::func);
        TimeUnit.MILLISECONDS.sleep(32);
        assertTrue(true);
        elapsed.get();
        fail("Should have thrown an exception");
    }
}