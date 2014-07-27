package io.delr3ves.restcheduler.resources;

import com.codahale.metrics.annotation.Timed;
import io.delr3ves.restcheduler.core.model.JobDescription;
import io.delr3ves.restcheduler.core.service.JobSchedulerService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Path("jobs")
public class JobResource {
    private JobSchedulerService schedulerService;

    @Inject
    public JobResource(JobSchedulerService scheduler) {
        this.schedulerService = scheduler;
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Timed
    public JobDescription createJob(@Valid JobDescription jobDescription) {
        return schedulerService.schedule(jobDescription);
    }

}
