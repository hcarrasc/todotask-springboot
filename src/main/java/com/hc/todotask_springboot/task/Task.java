package com.hc.todotask_springboot.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Table
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String description;
    private boolean done;
    private Instant createTime;

    public Task(Long id, String title, String description, boolean done, Instant createTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
        this.createTime = createTime;
    }

    public Task() {

    }

}
