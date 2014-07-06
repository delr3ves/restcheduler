package io.delr3ves.restcheduler;

import com.google.inject.AbstractModule;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class RestchedulerGuiceModule extends AbstractModule {
    private RestchedulerConfiguration configuration;

    public RestchedulerGuiceModule(RestchedulerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(Scheduler.class).toInstance(getScheduler());
    }

    private Scheduler getScheduler() {
        try {
            return StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
