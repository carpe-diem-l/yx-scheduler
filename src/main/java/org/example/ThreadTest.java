package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyuxin
 * @date 2024/4/16 10:47
 *
 * 该类模拟多线程并发场景
 * 任务1、2、3、4、5
 * 任务3依赖于任务1、2
 * 任务5依赖于任务3、4
 * [Task 1] -----> [Task 3] ------\
 *                /                   \
 * [Task 2] -----                    --> [Task 5]
 *                \                   /
 * [Task 4] -----------------------/
 */
public class ThreadTest {
    public static void main(String[] args) throws Exception{
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}
            System.out.println("执行了任务1");
            return "Task 1";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}
            System.out.println("执行了任务2");
            return "Task 2";
        });

        CompletableFuture<String> task4 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}
            System.out.println("执行了任务4");
            return "Task 4";
        });

        CompletableFuture<Void> group1 = task1.thenCombine(task2, (result1, result2) -> {
            System.out.println(result1);
            System.out.println(result2);
            return null;
        });

        CompletableFuture<String> task3 = group1.thenApply(result -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}
            System.out.println("执行了任务3");
            return "Task 3";
        });

        CompletableFuture<Void> group2 = task3.thenCombine(task4, (result3, result4) -> {
            System.out.println(result3);
            System.out.println(result4);
            return null;
        });

        CompletableFuture<Void> task5 = group2.thenAccept(result -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}
            System.out.println("执行了任务5");
            System.out.println("Task 5");
        });
        task5.join();
    }

}
