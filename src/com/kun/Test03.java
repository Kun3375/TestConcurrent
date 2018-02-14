package com.kun;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 18:05
 */
public class Test03 {
    
    public static void main(String[] args) {
        
        Source03 source03 = new Source03();
        
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                source03.printNumber();
            }
        }).start();
        
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                source03.printWord();
            }
        }).start();
    }
}

class Source03 {
    
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private char word = 'A';
    private Integer num = 1;
    
    public void printNumber(){
        lock.lock();
        try {
            if (flag == 2) {
                condition.await();
            }
            if (num == 53){
                num = 1;
            }
            System.out.println("num = " + num++);
            flag = 2;
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printWord(){
        lock.lock();
        try {
            if (flag == 1) {
                condition.await();
            }
            if (word > 'Y'){
                word = 'A';
            }
            System.out.println("word = " + word++ + word);
            flag = 1;
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
}