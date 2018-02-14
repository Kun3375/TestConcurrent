package com.kun;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 16:10
 */
public class Test02 {
    
    public static void main(String[] args) {
        Source02 source02 = new Source02();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source02.print5();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source02.print10();
            }
        }).start();new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source02.print15();
            }
        }).start();
    }
}

class Source02 {
    
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    
    public void print5() {
        lock.lock();
        try {
            if (flag != 1){
                c1.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t5");
            flag = 2;
            c2.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public void print10() {
        lock.lock();
        try {
            if (flag != 2){
                c2.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t10");
            flag =3;
            c3.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public void print15() {
        lock.lock();
        try {
            if (flag != 3){
                c3.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t15");
            flag = 1;
            c1.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
}