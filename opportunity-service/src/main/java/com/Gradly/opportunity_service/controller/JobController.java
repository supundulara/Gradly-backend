package com.Gradly.opportunity_service.controller;

import com.Gradly.opportunity_service.model.Job;
import com.Gradly.opportunity_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Job createJob(
            @RequestBody Job job,
            @RequestHeader("X-User-Id") String userId
    ){
        return jobService.createJob(job, userId);
    }

    @GetMapping
    public List<Job> getJobs(){
        return jobService.getJobs();
    }

    @GetMapping("/{id}")
    public Job getJob(@PathVariable String id){
        return jobService.getJob(id);
    }

    @PostMapping("/{id}/apply")
    public void apply(
            @PathVariable String id,
            @RequestHeader("X-User-Id") String userId
    ){
        jobService.apply(id, userId);
    }
}