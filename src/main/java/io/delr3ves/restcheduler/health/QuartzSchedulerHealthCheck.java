package io.delr3ves.restcheduler.health;

import com.codahale.metrics.health.HealthCheck;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.inject.Inject;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class QuartzSchedulerHealthCheck extends HealthCheck {

    private Scheduler scheduler;

    @Inject
    public QuartzSchedulerHealthCheck(Scheduler scheduler)  throws SchedulerException {
        this.scheduler = scheduler;
    }

    /**
     * Checks the state of the Quartz Scheduler and all scheduled Quartz jobs
     * @return Result Healthy if the scheduler and all of it's jobs are running withing acceptable parameters
     * @throws Exception if unable to check the state of the scheduler or its jobs
     */
    @Override
    protected Result check() throws Exception {
        scheduler.isStarted();
        // The Quartz is currently in an error state
        if (!scheduler.isStarted())
            return Result.unhealthy("Scheduler is not running");
        else
            return Result.healthy();
    }
}
