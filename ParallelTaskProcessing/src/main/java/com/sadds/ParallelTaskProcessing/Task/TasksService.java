package com.sadds.ParallelTaskProcessing.Task;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TasksService {

    private final TasksRepository tasksRepository;
    private final TaskQueueService taskQueueService;
    private static final Logger logger = LoggerFactory.getLogger(TasksService.class);

    public TasksService(TasksRepository tasksRepository, TaskQueueService taskQueueService) {
        this.tasksRepository = tasksRepository;
        this.taskQueueService = taskQueueService;
    }

    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    public Tasks getById(int taskId) {
        return tasksRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException("Task not found with id " + taskId));
    }

    public Tasks createTaskService(Tasks task) {
        task.setStatus(Status.PENDING);
        task.setTimestamp(LocalDateTime.now());
        Tasks savedTask = tasksRepository.save(task);
        taskQueueService.addTask(savedTask);
        logger.info("Status of {} is {}", savedTask, savedTask.getStatus());
        return savedTask;
    }

    public Tasks updateTask(Integer taskId, Tasks newTask) {
        Tasks oldTask = getById(taskId);

        if (newTask.getPriority() != null) {
            if (!oldTask.getStatus().equals(Status.PENDING)) {
                throw new TaskNotPending("Task is not pending so cannot update");
            } else {
                changePriority(oldTask, newTask.getPriority());
            }
        }
        if (newTask.getDescription() != null) {
            oldTask.setDescription(newTask.getDescription());
        }

        return tasksRepository.save(oldTask);
    }

    public void changePriority(Tasks oldTask, Integer priority) {;
        oldTask.setPriority(priority);
        System.out.println("Changed priority of task : "+ oldTask + " ,to :" + priority);
        taskQueueService.addTask(oldTask);
    }

    public void deleteTask(Integer taskId) throws InterruptedException {
        var task = tasksRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException("Task not found with id " + taskId));
        task.setStatus(Status.STOPPED);
        System.out.println("Task " + taskId + " is stopped");
        tasksRepository.deleteById(taskId);
    }
}
