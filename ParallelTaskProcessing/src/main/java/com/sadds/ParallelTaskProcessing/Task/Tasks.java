package com.sadds.ParallelTaskProcessing.Task;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Tasks implements Comparable<Tasks> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer priority;
    @CreatedDate
    private LocalDateTime timestamp;

    @Override
    public int compareTo(Tasks object) {
        return Integer.compare(object.priority, this.priority);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Tasks [id=" + id + ", description=" + description + ", status=" + status + ", priority=" + priority + "]";
    }


}
