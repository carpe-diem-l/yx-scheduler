package org.example;



import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyuxin
 * @date 2024/4/16 10:34
 */
public class Main {

    public static String asyncTask(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Hello, CompletableFuture";
    }

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(Main::asyncTask);
        future.join();
        future.thenAccept(result -> System.out.println("Result: " + result));
        // 主线程结束
        System.out.println("Main thread ends.");
    }

}