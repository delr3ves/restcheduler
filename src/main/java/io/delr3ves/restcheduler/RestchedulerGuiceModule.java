package io.delr3ves.restcheduler;

import com.google.inject.AbstractModule;
import io.delr3ves.restcheduler.core.service.JobSchedulerService;
import io.delr3ves.restcheduler.core.service.JobSchedulerServiceQuartzImpl;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class RestchedulerGuiceModule extends AbstractModule {
    private RestchedulerConfiguration configuration;
    private HibernateBundle hibernate;

    public RestchedulerGuiceModule(RestchedulerConfiguration configuration, HibernateBundle hibernate) {
        this.configuration = configuration;
        this.hibernate = hibernate;
    }

    @Override
    protected void configure() {
        bind(Scheduler.class).toInstance(getScheduler());
        bind(HibernateBundle.class).toInstance(hibernate);
        bind(SessionFactory.class).toInstance(hibernate.getSessionFactory());
        bind(RestchedulerConfiguration.QuartzConfiguration.class).toInstance(configuration.getQuartzSettings());
        bind(JobSchedulerService.class).to(JobSchedulerServiceQuartzImpl.class);
    }

    private Scheduler getScheduler() {
        try {
            SchedulerFactory sf = new StdSchedulerFactory(configuration.getSchedulerFactoryProperties());
            Scheduler scheduler = sf.getScheduler();
            return scheduler;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
