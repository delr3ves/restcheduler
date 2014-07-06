package io.delr3ves.restcheduler.managed;

import io.dropwizard.lifecycle.Managed;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import javax.inject.Inject;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class QuartzSchedulerManager implements Managed {

    private Scheduler scheduler;

    @Inject
    public QuartzSchedulerManager(Scheduler scheduler) throws SchedulerException {
        this.scheduler = scheduler;
    }

    @Override
    public void start() throws Exception {
        scheduler.start();
    }

    @Override
    public void stop() throws Exception {
        scheduler.shutdown(true);
    }

}
