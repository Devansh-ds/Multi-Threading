package com.sadds.ParallelTaskProcessing.Task;

public class TaskNotPending extends RuntimeException {

    public TaskNotPending(String message) {
        super(message);
    }
}
