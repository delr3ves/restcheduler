package io.delr3ves.restcheduler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.delr3ves.restcheduler.health.QuartzSchedulerHealthCheck;
import io.delr3ves.restcheduler.managed.QuartzSchedulerManager;
import io.delr3ves.restcheduler.resources.JobResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class RestchedulerApplication extends Application<RestchedulerConfiguration> {
    public static void main(String[] args) throws Exception {
        new RestchedulerApplication().run(args);
    }

    @Override
    public String getName() {
        return "RestCheduler";
    }

    @Override
    public void initialize(Bootstrap<RestchedulerConfiguration> bootstrap) {

    }

    @Override
    public void run(RestchedulerConfiguration configuration,
                    Environment environment) {
        Injector injector = Guice.createInjector(new RestchedulerGuiceModule(configuration));

        environment.healthChecks().register("Quartz", injector.getInstance(QuartzSchedulerHealthCheck.class));
        environment.lifecycle().manage(injector.getInstance(QuartzSchedulerManager.class));

        environment.jersey().register(injector.getInstance(JobResource.class));
    }
}

