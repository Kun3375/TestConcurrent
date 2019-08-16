package com.kun;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 16:10
 */
public class TestCondition {
    
    public static void main(String[] args) {
        Source source = new Source();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source.print5();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source.print10();
            }
        }).start();new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source.print15();
            }
        }).start();
    }

    private static class Source {

        private int flag = 1;
        private Lock lock = new ReentrantLock();
        private Condition c1 = lock.newCondition();
        private Condition c2 = lock.newCondition();
        private Condition c3 = lock.newCondition();

        private void print5() {
            lock.lock();
            try {
                if (flag != 1) {
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

        private void print10() {
            lock.lock();
            try {
                if (flag != 2) {
                    c2.await();
                }
                System.out.println(Thread.currentThread().getName() + "\t10");
                flag = 3;
                c3.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        private void print15() {
            lock.lock();
            try {
                if (flag != 3) {
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
}
