package com.kun;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author kun
 * @version 2019-08-16 17:23
 */
public class TestForkJoinPool {

    private static ForkJoinPool pool = new ForkJoinPool();

    public static void main(String[] args) {
        int[] ints = IntStream.rangeClosed(1, 100).toArray();

        // invoke 直接获取任务结果，不需要阻塞到获取结果则使用 submit
        // RecursiveTask 递归任务，RecursiveAction 不需要结果的递归任务，CountedCompleter
        Integer result = pool.invoke(new SumTask(ints, 0, 100));
        System.out.println(result);

    }

    private static class SumTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = -5864664223669381980L;

        private int[] ints;
        private int start;
        private int end;

        private SumTask(int[] ints, int start, int end) {
            this.ints = ints;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start < 8) {
                return calc(ints, start, end);
            }
            int middle = ((end - start) >> 1) + start;
            ForkJoinTask<Integer> forkTask = new SumTask(ints, middle, end).fork();
            return new SumTask(ints, start, middle).compute() + forkTask.join();
        }

        private Integer calc(int[] ints, int start, int end) {
            int result = 0;
            for (int i = start; i < end; i++) {
                result += ints[i];
            }
            return result;
        }
    }
}
