package io.delr3ves.restcheduler.core.service;

import io.delr3ves.restcheduler.core.model.JobDescription;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public interface JobSchedulerService {

    /**
     *
     * @param jobDescription
     * @return
     */
    JobDescription schedule(JobDescription jobDescription);
}
