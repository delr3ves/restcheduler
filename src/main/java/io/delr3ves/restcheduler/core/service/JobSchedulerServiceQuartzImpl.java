package io.delr3ves.restcheduler.core.service;

import io.delr3ves.restcheduler.core.jobs.RestJob;
import io.delr3ves.restcheduler.core.jobs.ShellJob;
import io.delr3ves.restcheduler.core.model.JobDescription;
import io.delr3ves.restcheduler.core.model.RestJobDescription;
import io.delr3ves.restcheduler.core.model.ShellJobDescription;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Slf4j
public class JobSchedulerServiceQuartzImpl implements JobSchedulerService {

    private Scheduler scheduler;
    private Map<Class<? extends JobDescription>, Class<? extends Job>> jobExchangeMatrix;

    @Inject
    public JobSchedulerServiceQuartzImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.jobExchangeMatrix = new HashMap<Class<? extends JobDescription>, Class<? extends Job>>() {{
            put(RestJobDescription.class, RestJob.class);
            put(ShellJobDescription.class, ShellJob.class);
        }};
    }

    @Override
    public JobDescription schedule(JobDescription jobDescription) {
        JobDetail job = buildJob(jobDescription);

        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(jobDescription.getExecutionDateUTC().toDate())
                .build();
        try {
            scheduler.scheduleJob(job, trigger);
            return jobDescription;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JobDetail buildJob(JobDescription jobDescription) {
        return JobBuilder.newJob(jobExchangeMatrix.get(jobDescription.getClass()))
                .withDescription(jobDescription.getDescription())
                .usingJobData(new JobDataMap(jobDescription.getExtraParams()))
                .build();
    }
}
