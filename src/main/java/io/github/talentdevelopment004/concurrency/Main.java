package io.github.talentdevelopment004.concurrency;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

//        oldWayThreads();
//        usingFuture();
        usingCompletableFuture();

    }

    private static void usingCompletableFuture() {
        System.out.println("Before calling the completable future");
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // Simulate a time-consuming computation
            int result=0;
            try {
                List<Integer> numbers = IntStream.range(1, 1000).boxed().collect(Collectors.toList());
                result= processAllData(numbers);
                System.out.println("Process all data completed");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("FIRST: "+ LocalTime.now());
            return result;
        });

        future.thenApply(result -> {
                    System.out.println("result 1 "+result);
                return result * 2;
                })
                .thenApply(result -> result * result)
                .thenAccept(finalResult -> {
                    System.out.println("Second: "+ LocalTime.now());
                    System.out.println("Result: " + finalResult);
                });

        // You can continue performing other tasks while the asynchronous computation is running
        System.out.println("After calling the completable future + preforming other tasks");
        // Block and wait for the result
        future.join();

    }

    private static void usingFuture() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("Before calling the future "+ Instant.now());
        Future<Integer> computeResultFuture = executor.submit(()->{
            List<Integer> numbers = IntStream.range(1, 1000).boxed().collect(Collectors.toList());
            return   processAllData(numbers);
        });
        System.out.println("After calling the future "+ Instant.now());
        try {
            Integer sum = computeResultFuture.get();
            System.out.println(Instant.now()+ " The sum of the numbers is: " + sum);
        } catch (ExecutionException e) {
           e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    public static int processAllData(List<Integer> data) {
        System.out.println("Processing all numbers from "+Instant.now());
        return  data.parallelStream().map(Main::processRecord).mapToInt(Integer::intValue).sum();
    }

    public static int processRecord(int input) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Handle interrupted exception
        }
        return input + 1;
    }

    private static void oldWayThreads() {
        MyThread myThread = new MyThread();
        myThread.start();

        Thread thread = new Thread(() -> {
            System.out.println("My thread is running ");
        });
        thread.start();
    }
}
