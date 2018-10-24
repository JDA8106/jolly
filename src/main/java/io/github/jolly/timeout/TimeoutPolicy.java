package io.github.jolly.timeout;

import io.github.jolly.policy.Policy;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * TimeoutPolicy class
 * @author Anish Visaria, Geetika Kapoor
 */
public class TimeoutPolicy extends Policy {

    private int duration;
    private boolean cancelFuture;

    private static final ScheduledExecutorService schedulerExecutor =
            Executors.newScheduledThreadPool(10);
    private static final ExecutorService executorService =
            Executors.newCachedThreadPool();

    /**
     * Initializes TimeoutPolicy with wait duration before timeout occurs and whether attempt is made to cancel future
     * @param duration wait duration before function times out
     * @param cancelFuture If the timeout is reached and an exception is thrown upstream,
     *                    then an attempt will be made to cancel the future if cancelFuture is true.
     *
     */
    public TimeoutPolicy(int duration, boolean cancelFuture) {
        this.duration = duration;
        this.cancelFuture = cancelFuture;
    }

    /**
     * Executes method with the given parameters duration and cancelFuture
     * @param function function to be executed with the TimeoutPolicy
     * @param <T>
     * @return output of method on success or exception on failure
     */
    public <T> T exec(Supplier<T> function) {

        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(duration))
                .cancelRunningFuture(cancelFuture)
                .build();

        TimeLimiter timeLimiter = TimeLimiter.of(config);

        Supplier<CompletableFuture<T>> futureSupplier = () -> CompletableFuture.supplyAsync(() -> function.get());

        Callable restrictedCall = TimeLimiter
                .decorateFutureSupplier(timeLimiter, futureSupplier);

        try {
            T res = (T) restrictedCall.call();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> CompletableFuture<T> runAsync(Supplier<T> function) {
        return supplyAsync(() -> exec(function), this.duration, TimeUnit.MILLISECONDS, null);
    }

    private static <T> CompletableFuture<T> supplyAsync(
            final Supplier<T> supplier, long timeoutValue, TimeUnit timeUnit,
            T defaultValue) {

        final CompletableFuture<T> cf = new CompletableFuture<>();

        Future<?> future = executorService.submit(() -> {
            try {
                cf.complete(supplier.get());
            } catch (Throwable ex) {
                cf.completeExceptionally(ex);
            }
        });

        //schedule watcher
        schedulerExecutor.schedule(() -> {
            if (!cf.isDone()) {
                cf.complete(defaultValue);
                future.cancel(true);
            }

        }, timeoutValue, timeUnit);

        return cf;
    }
}
