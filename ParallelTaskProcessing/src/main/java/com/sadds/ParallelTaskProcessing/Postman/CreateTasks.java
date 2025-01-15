package com.sadds.ParallelTaskProcessing.Postman;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateTasks {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Random random = new Random();

        String url = "http://localhost:8080/tasks";
        List<String> tasks = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            int priority = random.nextInt(10) + 1;

            String buildTask = String.format("""
                {
                    "description": "Task %d",
                    "status": "PENDING",
                    "priority": %d
                }
                """, i, priority);

            tasks.add(buildTask);
        }

        System.out.println("Creating tasks...");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        for (String task : tasks) {
            executorService.submit(() -> {
                HttpEntity<String> request = new HttpEntity<>(task, headers);
                restTemplate.postForObject(url, request, String.class);
                System.out.println("Task sent: " + task);
            });
            System.out.println("Waiting for task...");
        }
        executorService.shutdown();


    }
}
