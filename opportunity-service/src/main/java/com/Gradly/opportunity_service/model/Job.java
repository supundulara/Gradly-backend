package com.Gradly.opportunity_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jobs")
public class Job {

    @Id
    private String id;

    private String title;
    private String company;
    private String description;
    private String location;

    private String postedBy;   // userId
    private String postedByName;

    private LocalDateTime createdAt;
    private LocalDateTime deadline;
}