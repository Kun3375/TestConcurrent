package com.kun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author CaoZiye
 * @version 1.0 2017/10/22 10:17
 */
public class TestSemaphore {
    
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static Source source = new Source();
    
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            executorService.submit(() -> {
                source.setName(Thread.currentThread().getName() + "-run");
                System.out.println("============================");
            });
        }
    }

    private static class Source {

        private String name = "Kun";
        private Semaphore semaphore = new Semaphore(2);

        public String getName(){
            return name;
        }

        private void setName(String name){
            try {
                semaphore.acquire();
                Thread.sleep(2000);
                this.name = name;
                System.out.println("MyNameIs:" + name);
                System.out.print("queueLength:" + semaphore.getQueueLength());
                System.out.print("\tdrainPermits:" + semaphore.drainPermits());
                System.out.println("\tavailablePermits" + semaphore.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

}




