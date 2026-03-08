package com.Gradly.opportunity_service.controller;

import com.Gradly.opportunity_service.model.Application;
import com.Gradly.opportunity_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final JobService jobService;

    @GetMapping("/me")
    public List<Application> getMyApplications(
            @RequestHeader("X-User-Id") String userId
    ){
        return jobService.getUserApplications(userId);
    }
}
