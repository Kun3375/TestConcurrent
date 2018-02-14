package com.kun;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author CaoZiye
 * @version 1.0 2017/10/22 11:07
 */
public class Test08 {
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Source08 source = new Source08();
        for (int i = 0; i < 10; i++) {
            executorService.submit(()->{
                try {
                    Thread.sleep((long) (Math.random() * 2000));
                    source.go();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
}

class Source08 {
    
    private CyclicBarrier cb = new CyclicBarrier(3);
    
    public void go(){
        System.out.println("numberWaiting:" + cb.getNumberWaiting()
                + "\tparties:" + cb.getParties());
        try {
            cb.await();
            System.out.println("can go");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
    
}
