package com.kun;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * @author CaoZiye
 * @version 1.0 2017/10/22 11:50
 */
public class Test10 {
    
    public static void main(String[] args) throws InterruptedException {
        Source10 source = new Source10();
        Set<String> set = new HashSet<>();
        new Thread(() -> {
            String name = source.toExechange("Messi");
            set.add(name);
        }).start();
        new Thread(() -> {
            String name = source.toExechange("Ronaldo");
            set.add(name);
        }).start();
        Thread.sleep(5000);
        System.out.println(set);
    }
    
}

class Source10 {
    
    private Exchanger<String> exchanger = new Exchanger<>();
    
    public String toExechange(String data) {
        System.out.println(Thread.currentThread().getName() + " data: " + data);
        String enchangeData = null;
        try {
            Thread.sleep(new Random().nextInt(3000));
            enchangeData = exchanger.exchange(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " data: " + enchangeData);
        return enchangeData;
    }
    
    
    
}
