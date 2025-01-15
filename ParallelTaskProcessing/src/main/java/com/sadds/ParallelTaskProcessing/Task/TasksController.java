package com.sadds.ParallelTaskProcessing.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TasksService tasksService;
    private static final Logger logger = LoggerFactory.getLogger(TasksController.class);

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public ResponseEntity<List<Tasks>> getAllTasks() {
        return ResponseEntity.ok(tasksService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable("taskId") int taskId) {
        return ResponseEntity.ok(tasksService.getById(taskId));
    }

    @PostMapping
    public ResponseEntity<Tasks> createTask(@RequestBody Tasks task) {
        logger.info("Create new task {}", task);
        return ResponseEntity.ok(tasksService.createTaskService(task));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Tasks> updateTask(@PathVariable("taskId") Integer taskId,
                                            @RequestBody Tasks newTask)
    {
        return ResponseEntity.ok(tasksService.updateTask(taskId, newTask));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTask(@PathVariable("taskId") Integer taskId) throws InterruptedException {
        tasksService.deleteTask(taskId);
        return ResponseEntity.ok("Deleted task with id " + taskId);
    }

}



















