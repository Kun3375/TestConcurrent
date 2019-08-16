package com.kun;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author kun
 * @version 2019-08-16 11:47
 */
public class TestCompletionStage {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("thread " + Thread.currentThread().getName());
        CountDownLatch latch = new CountDownLatch(1);

        CompletionStage<Integer> future = CompletableFuture
                // completedFuture supply a initial value
                // allOf 汇集多个任务异步结果
                // supply or run or another future
//                .runAsync()
                .supplyAsync(() -> {
                    System.out.println("first. thread " + Thread.currentThread().getName());
                    return 10;
                })
                // accept 消耗无产出，apply 消耗并产出，run 不消耗不产出即不关心前一阶段结果
                // or async 继续异步执行
                .thenApply(i -> {
                    System.out.println("stage two. thread " + Thread.currentThread().getName());
                    return i + 10;
                })
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> {
                            System.out.println("run another task. thread " + Thread.currentThread().getName());
                            return 5;
                        }), (i, j) -> {
                            System.out.println("combine i + j = " + (i + j));
                            return i + j;
                        }
                )
                // when complete doing or check result
                .whenComplete((i, e) -> {
                    System.out.println("complete. thread " + Thread.currentThread().getName());
                    try {
                        if (e != null) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(i);
                    } finally {
                        latch.countDown();
                    }
                });
        latch.await(10, TimeUnit.SECONDS);
    }

}
