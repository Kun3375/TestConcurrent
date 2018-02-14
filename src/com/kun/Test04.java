package com.kun;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 18:30
 */
public class Test04 {
    
    //多个写锁只能有一个在写（写的时候不能读），多个读锁可以一起读（读的时候不能写）
    public static void main(String[] args) {
        Source04 source04 = new Source04();
        
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source04.setNum(i);
            }
        }).start();
    
        new Thread(() -> {
            for (int i = 10; i < 15; i++) {
                source04.setNum(i);
            }
        }).start();
        
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source04.getNum();
            }
        }).start();
    
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source04.getNum();
            }
        }).start();
    }
    
}

class Source04{
    
    private Integer num = 0;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public void setNum(Integer num){
        lock.writeLock().lock();
        try {
            this.num = num;
            TimeUnit.SECONDS.sleep(1);
            System.out.println("正在写入");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public Integer getNum(){
        lock.readLock().lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("正在读出" + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        return num;
    }
    
}
