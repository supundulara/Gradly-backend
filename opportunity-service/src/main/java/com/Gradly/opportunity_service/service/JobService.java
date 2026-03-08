package com.Gradly.opportunity_service.service;

import com.Gradly.opportunity_service.client.UserClient;
import com.Gradly.opportunity_service.dto.CreateJobRequest;
import com.Gradly.opportunity_service.dto.UserResponse;
import com.Gradly.opportunity_service.model.Application;
import com.Gradly.opportunity_service.model.Job;
import com.Gradly.opportunity_service.repository.ApplicationRepository;
import com.Gradly.opportunity_service.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserClient userClient;

    public Job createJob(CreateJobRequest request, String userId) {

        UserResponse user = userClient.getUser(userId);

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());

        job.setPostedBy(userId);
        job.setPostedByName(user.getName());

        job.setDeadline(request.getDeadline());
        job.setCreatedAt(LocalDateTime.now());

        return jobRepository.save(job);
    }

    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    public Job getJob(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public void apply(String jobId, String userId) {

        if(applicationRepository.existsByJobIdAndUserId(jobId, userId)) {
            throw new RuntimeException("You have already applied for this job");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if(job.getPostedBy().equals(userId)){
            throw new RuntimeException("You cannot apply to your own job");
        }

        Application application = new Application();
        application.setJobId(jobId);
        application.setUserId(userId);
        application.setAppliedAt(LocalDateTime.now());

        applicationRepository.save(application);
    }

    public List<Application> getUserApplications(String userId){
        return applicationRepository.findByUserId(userId);
    }
}
