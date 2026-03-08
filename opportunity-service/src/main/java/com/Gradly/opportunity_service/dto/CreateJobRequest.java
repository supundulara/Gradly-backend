package com.Gradly.opportunity_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobRequest {

    private String title;
    private String company;
    private String description;
    private String location;
    private LocalDateTime deadline;

}