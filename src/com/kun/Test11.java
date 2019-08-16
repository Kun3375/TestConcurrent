package com.kun;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author kun
 * @version 2019-08-16 11:47
 */
public class Test11 {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        CompletionStage<Integer> future = CompletableFuture
                // supply or run or another future
//                .runAsync()
                .supplyAsync(() -> 10)
                // accept 消耗无产出，apply 消耗并产出，run 不消耗不产出即不关心前一阶段结果
                // or async
                .thenApply((i) -> i + 10)
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> {
                            System.out.println("run another task");
                            return 5;
                        }), (i, j) -> {
                            System.out.println("combine i + j = " + (i + j));
                            return i + j;
                        }
                )
                // when complete doing or check result
                .whenComplete((i, e) -> {
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
