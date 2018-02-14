package com.kun;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 16:07
 */
public class Test01 {
    
    public static void main(String[] args) {
        
        Source01 source01 = new Source01();
        
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                source01.incre();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                source01.decre();
            }
        }).start();
    }
}

class Source01 {
    
    private int count = 0;
    
    public int getCount() {
        return count;
    }
    
    public synchronized void incre() {
        try {
            while (count != 0) {
                wait();
            }
            System.out.println("incred:" + ++count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }
    
    public synchronized void decre() {
        try {
            while (count == 0) {
                wait();
            }
            System.out.println("decred:" + --count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }
}
