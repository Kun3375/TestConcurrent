package com.kun;

import java.util.concurrent.*;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 19:05
 */
public class TestScheduleExecutor {

    // 可调度线程池
    private static ScheduledExecutorService SCHEDULE = Executors.newScheduledThreadPool(5);
    
    public static void main(String[] args) {
        
        Source source = new Source();

        ScheduledFuture<String> result;
    
        try {
            for (int i = 0; i < 5; i++) {
                result = SCHEDULE.schedule(source::say,1,TimeUnit.SECONDS);
                System.out.println("result = " + result.get(3,TimeUnit.SECONDS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SCHEDULE.shutdown();
        }
    
    }

    private static class Source {
        private String say(){
            System.out.println( Thread.currentThread().getId() + "\t输出......");
            return "OK";
        }
    }
    
}



