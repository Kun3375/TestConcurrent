package com.kun;

/**
 * @author CaoZiye
 * @version 1.0 2017/9/5 16:07
 */
public class TestSynchronized {
    
    public static void main(String[] args) {
        
        Source source = new Source();
        
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                source.incre();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                source.decre();
            }
        }).start();
    }

    private static class Source {

        private int count = 0;

        public int getCount() {
            return count;
        }

        private synchronized void incre() {
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

        private synchronized void decre() {
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
}

