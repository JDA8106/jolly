---
layout: post
title: Retry Pattern
---
Retry Pattern
---
### 1.1 How Retry Works
#### 1.1.1 How Synchronous Works
When a method is executed through the policy:
1. The `RetryPolicy` attempts the method passed in with .exec().
   - If the method executes successfully, the return value (if relevant) is returned and the policy exits.
   - If the method throws an exception, it:
     1. waits 500 milisecond by default (user configurable)
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

