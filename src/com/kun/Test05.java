package com.kun;

import java.util.concurrent.*;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 19:05
 */
public class Test05 {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        
        Source05 source05 = new Source05();
    
/*        ExecutorService fixed = Pool.getFixed();
        Future<String> result;
    
        for (int i = 0; i < 5; i++) {
            result = fixed.submit(source05::say);
            System.out.println(result.get());
        }*/
    
/*        ExecutorService cached = Pool.getCached();
    
        for (int i = 0; i < 30; i++) {
            cached.submit(source05::say);
        }*/
    
        ScheduledExecutorService schedule = Pool.getSchedule();
        ScheduledFuture<String> result;
    
        try {
            for (int i = 0; i < 5; i++) {
                result = schedule.schedule(source05::say,1,TimeUnit.SECONDS);
                System.out.println("result = " + result.get(3,TimeUnit.SECONDS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            schedule.shutdown();
        }
    
    }
    
}

class Source05 {
    public String say(){
        System.out.println( Thread.currentThread().getId() + "\t输出......");
        return "OK";
    }
}

class Pool{
    
    //单线程池
    private static ExecutorService single = Executors.newSingleThreadExecutor();
    
    //自适应线程池
    private static ExecutorService cached = Executors.newCachedThreadPool();
    //定量线程池
    private static ExecutorService fixed = Executors.newFixedThreadPool(5);
    //可调度线程池
    private static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(5);
    
    public static ExecutorService getSingle() {
        return single;
    }
    
    public static ExecutorService getCached() {
        return cached;
    }
    
    public static ExecutorService getFixed() {
        return fixed;
    }
    
    public static ScheduledExecutorService getSchedule() {
        return schedule;
    }
}
