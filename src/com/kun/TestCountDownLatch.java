package com.kun;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author CaoZiye
 * @version 1.0 2017/10/22 11:32
 */
public class TestCountDownLatch {
    
    public static void main(String[] args) throws InterruptedException {
        Source source = new Source();
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> source.game(finalI)).start();
        }
        Thread.sleep(2000);
        source.startAndEnd();
    }

    private static class Source {
        private CountDownLatch startLatch = new CountDownLatch(1);
        private CountDownLatch endLatch = new CountDownLatch(3);

        private void startAndEnd() {
            System.out.println("=== start ===");
            startLatch.countDown();
            try {
                endLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=== end ===");
        }

        private void game(int i) {
            System.out.println("No." + i + " is ready");
            try {
                startLatch.await();
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("No." + i + " completed");
            endLatch.countDown();
        }
    }
}