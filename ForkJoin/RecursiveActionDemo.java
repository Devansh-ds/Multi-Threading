package com.sadds.ForkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RecursiveActionDemo {
    public static void main(String[] args) {
        try (ForkJoinPool pool =
                     new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            WorkLoadSplitter splitter = new WorkLoadSplitter(128L);
            pool.invoke(splitter);
        }

    }
}

class WorkLoadSplitter extends RecursiveAction {

    private final Long workLoad;

    public WorkLoadSplitter(Long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if (workLoad > 16) {
            System.out.println("Work load is too large. so splitting : " + workLoad);
            long firstWorkLoad = workLoad / 2;
            long secondWorkLoad = workLoad - firstWorkLoad;

            WorkLoadSplitter firstSplit = new WorkLoadSplitter(firstWorkLoad);
            WorkLoadSplitter secondSplit = new WorkLoadSplitter(secondWorkLoad);

            firstSplit.fork();
            secondSplit.fork();
        } else {
            System.out.println("Work load within limits : " + workLoad);
        }
    }

}
