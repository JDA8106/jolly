# The Jolly Project

![The Jolly Logo](https://github.com/JDA8106/jolly/raw/master/jolly2.png)

The Jolly project is a simple fault-tolerance library inspired by the Polly Project.

Retry Pattern
---
### 1.1 How Retry Works
#### 1.1.1 How Synchronous Works
When a method is executed through the policy:
1. The `RetryPolicy` attempts the method passed in with .exec().
   - If the method executes successfully, the return value (if relevant) is returned and the policy exits.
   - If the method throws an exception, it:
     1. waits 500 miliseconds by default (user configurable)
     2. tries again up to 3 times by default (user configurable).
     3. If it fails everytime, it throws the last exception.

#### 1.1.2 How Asynchronous Works
When a method is executed through the policy:
1. The `RetryPolicy` attempts the method passed in with .runAsync().
   - A Java future is returned that will contain the return value once the policy exits
   - The policy will execute the same way as synchronous (refer to 1.1.1)

### 1.2 Retry Usage
#### 1.2.1 How to Build
The code below builds a `RetryPolicy` with 3 attempts and a 500ms wait duration.
```java
RetryPolicy pol = new RetryPolicyBuilder().build();
```
The code below builds a `RetryPolicy` with 5 attempts and a 1000ms wait duration.
```java
RetryPolicy pol = new RetryPolicyBuilder()
                .attempts(5)
                .waitDuration(1000)
                .build();
```
#### 1.2.2 How to Use Synchronous
Then use your `RetryPolicy` to execute a `Supplier` with retries:
```java
String result = pol.exec(backendService::doSomething);
```
#### 1.2.3 How to Use Asynchronous
Then use your `RetryPolicy` to execute a `Supplier` with retries:
```java
CompletableFuture<String> result = pol.runAsync(backendService::doSomething);
```
Later on, you can get the result of the `RetryPolicy` as follows:
```java
String actualResult = result.get()
```

Circuit-breaker Pattern
---
### 1.1 How Circuit-breaker Works
Circuit-breaker is a state machine of three states: open, closed, half-open. 
#### 1.1.1 How Synchronous Works
1. The circuit initially starts closed. When closed, the circuit-breaker executes methods placed through it, measuring the faults and successes.
   - If the failures exceed a certain `sizeRingBufferClosed`, 2 times by default (user configurable), the circuit will transition to open state.
   - NOTE: the libary has a `rateThreshold`, 100% by default (user configurable), which is the failure rate threshold in percentage above which the CircuitBreaker should trip open and start short circuiting calls
2. When opened, no methods will be executed.
   - If a certain duration of time, 1000 miliseconds by default, (user configurable) has elapsed:
      1. Circuit-breaker transitions to half-open state when method is passed through.
      2. Circuit-breaker transitions to half-open if function is queried. 
3. When half-open:
   - Next function will be treated as trial, to determine the circuit's condition.
      1. if function takes longer than `duration` to return, one additional attempt will occur per `duration`.
      2. if this call throws a exception, the circuit transitions back to open, and remains open again for `duration`.
      3. if the call succeeds, the circuit transitions back to closed.

#### 1.1.2 How Asynchronous Works
When a method is executed through the policy:
1. The `CircuitBreakerPolicy` attempts the method passed in with .runAsync().
   - A Java future is returned that will contain the return value once the policy exits
   - The policy will execute the same way as synchronous (refer to 1.1.1)

### 1.2 Circuit-breaker Usage
#### 1.2.1 How to Build
The code below builds a `CircuitBreakerPolicy` with `rateThreshold` 100%, a wait `duration` of 1000 ms, and ring buffer size of 2 for both half-open and closed states. 
```java
CircuitBreakerPolicy pol = new CircuitBreakerPolicyBuilder().build();
```
The code below builds a `CircuitBreakerPolicy` with `rateThreshold` 90%, a wait `duration` of 500 ms, and ring buffer size of 2 for half-open state and 1 for closed state. 
```java
CircuitBreakerPolicy pol = new CircuitBreakerPolicyBuilder()
                          .rateThreshold(90)
                          .duration(500)
                          .sizeRingBufferHalfOpen(2)
                          .sizeRingBufferClosed(1)
                          .build();
```
#### 1.2.2 How to Use Synchronous
Then use your `CircuitBreakerPolicy` to execute a `Supplier` with retries:
```java
String result = pol.exec(backendService::doSomething);
```
#### 1.2.3 How to Use Asynchronous
Then use your `CircuitBreakerPolicy` to execute a `Supplier` with retries:
```java
CompletableFuture<String> result = pol.runAsync(backendService::doSomething);
```
Later on, you can get the result of the `CircuitBreakerPolicy` as follows:
```java
String actualResult = result.get()
```

Timeout Pattern
---
### 1.1 How Timeout Works
#### 1.1.1 How Synchronous Works
When a method is executed through the policy:
1. The `TimeoutPolicy` attempts the method passed in with .exec().
   - If the method executes within the configured time, the return value (if relevant) is returned and the policy exits.
   - If the method takes longer than the configured time:
     1. the TimeoutPolicy throws an exception
     2. the TimeoutPolicy attempts to cancel the supplied future (user configurable)

#### 1.1.2 How Asynchronous Works
When a method is executed through the policy:
1. The `TimeoutPolicy` attempts the method passed in with .runAsync().
   - A Java future is returned that will contain the return value once the policy exits
   - The policy will execute the same way as synchronous (refer to 1.1.1)

### 1.2 Timeout Usage
#### 1.2.1 How to Build
The code below builds a `TimeoutPolicy` with a time duration of 1 second and a ‘yes’ to attempting to cancel the future in case it an exception occurs
```java
TimeoutPolicy pol = new TimeoutPolicyBuilder().build();
```
The code below builds a `TimeoutPolicy` with a time duration of 60 seconds and a ‘no’ to attempting to cancel the future in case it an exception occurs.
```java
TimeoutPolicy pol = new TimeoutPolicyBuilder()
                .duration(Duration.ofSeconds(60))
                .cancelFuture(false)
                .build();
```
#### 1.2.2 How to Use Synchronous
Then use your `TimeoutPolicy` to execute a `Supplier` with retries:
```java
String result = pol.exec(backendService::doSomething);
```
#### 1.2.3 How to Use Asynchronous
Then use your `TimeoutPolicy` to execute a `Supplier` with retries:
```java
CompletableFuture<String> result = pol.runAsync(backendService::doSomething);
```
Later on, you can get the result of the `TimeoutPolicy` as follows:
```java
String actualResult = result.get()
```


Cache Pattern
---
### 1.1 How Cache Works
`CachePolicy` is an implementation of the cache-aside pattern. It provides results from the cache or a user specified function.
`CachePolicy` uses Java's CacheManager to establish, configure and close Caches.

#### 1.1.1 How Synchronous Works
When .exec() method is executed through the policy:
1. `CachePolicy` takes in a `cacheKey` through the method parameter.
2. `CachePolicy` fetches result from a previously cached function specified by the user during initialization of the `CachePolicy`
3. If the cache retrieval fails because of a cache miss or an error, `CachePolicy` handles the exception.

### 1.2 Cache Usage
#### 1.2.1 How to Build
The code below builds a `CachePolicy` with a given function
```java
CachePolicy pol = new CachePolicy(function)
```
`CachePolicy` constructor does not have further parameters that users can specify, except the function of type `Supplier<V>`.

#### 1.2.2 How to Use Synchronous
Then use your `CachePolicy` to execute a `Supplier`:
```java
String result = pol.exec(backendService::doSomething);
```
#### 1.2.3 How to Use Asynchronous
Then use your `CachePolicy` to execute a `Supplier`:
```java
CompletableFuture<String> result = pol.runAsync(backendService::doSomething);
```
Later on, you can get the result of the `CachePolicy` as follows:
```java
String actualResult = result.get()
```