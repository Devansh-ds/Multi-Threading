package com.sadds.ParallelTaskProcessing.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.PriorityBlockingQueue;

@Service
public class TaskQueueService {

    PriorityBlockingQueue<Tasks> taskQueue;
    private static final Logger log = LoggerFactory.getLogger(TaskQueueService.class);

    public TaskQueueService() {
        taskQueue = new PriorityBlockingQueue<>();
    }

    public void addTask(Tasks task) {
        taskQueue.put(task);
        log.info("Adding task: {}", task);
    }

    public Tasks removeTask() {
        try {
            return taskQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getTaskSize() {
        return taskQueue.size();
    }

}
