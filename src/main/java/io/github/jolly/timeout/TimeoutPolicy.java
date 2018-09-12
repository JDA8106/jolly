package io.github.jolly.timeout;

import io.github.jolly.policy.Policy;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class TimeoutPolicy extends Policy{
    private int duration;
    private boolean cancelFuture;

    /**
     * Initializes TimeoutPolicy with wait duration before timeout occurs and whether attempt is made to cancel future
     * @param duration wait duration between attempts
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
                .timeoutDuration(Duration.ofSeconds(60))
                .cancelRunningFuture(true)
                .build();

        TimeLimiter timeLimiter = TimeLimiter.of(config);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

//        Supplier<Future<Integer>> futureSupplier = () -> executorService.submit(function);
//
//        Callable restrictedCall = TimeLimiter
//                .decorateFutureSupplier(timeLimiter, futureSupplier);
//
//        Try<T> result = Try.of(restrictedCall.call)
//                .onFailure(throwable -> LOG.info("A timeout possibly occurred."));
//
//        return result.get();
        return null;
    }
}
