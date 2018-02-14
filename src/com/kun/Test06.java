package com.kun;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 19:39
 */

public class Test06 {

}

class Singleton{
    
    private static Singleton singleton;
    private Singleton(){}
    
    public static Singleton getSingleton(){
        if(singleton != null){
            return singleton;
        }
        synchronized (Singleton.class){
            if (singleton == null) {
                singleton = new Singleton();
            }
        }
        return singleton;
    }
}

