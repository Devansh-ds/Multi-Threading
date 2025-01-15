package com.sadds.ParallelTaskProcessing.Task;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TaskConsumerThread {

    private final TaskQueueService taskQueueService;
    private final TasksRepository tasksRepository;
    private static final Logger log = LoggerFactory.getLogger(TaskConsumerThread.class);

    public TaskConsumerThread(TaskQueueService taskQueueService, TasksRepository tasksRepository) {
        this.taskQueueService = taskQueueService;
        this.tasksRepository = tasksRepository;
    }

    @PostConstruct
    public void consumeTasks() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down executor...");
            executorService.shutdown();
        }));

        // Consuming tasks indefinitely
        Runnable taskConsumer = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Remove task from the queue
                    Tasks task = taskQueueService.removeTask();
                    if (task == null) {
                        Thread.sleep(1000); // Sleep if no tasks are available
                        continue;
                    }

                    task.setStatus(Status.RUNNING);
                    Tasks savedTask = tasksRepository.save(task);

                    log.info("{} is Running", task);

                    Thread.sleep(10000);

                    // Randomly determine task outcome
                    Random random = new Random();
                    int chances = random.nextInt(10) + 1;

                    if (chances < 3) {
                        savedTask.setStatus(Status.FAILED);
                    } else {
                        savedTask.setStatus(Status.COMPLETED);
                    }

                    if (savedTask.getStatus() == Status.COMPLETED) {
                        log.info("{} is Completed", savedTask);
                    } else {
                        log.warn("{} is Failed", savedTask);
                    }

                    tasksRepository.save(savedTask);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("Error processing task: " + e.getMessage());
                }
            }
        };

        // Submit multiple consumer threads
        int cores = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < cores; i++) {
            executorService.submit(taskConsumer);
        }
    }
}
