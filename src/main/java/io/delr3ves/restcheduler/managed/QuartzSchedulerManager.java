package io.delr3ves.restcheduler.managed;

import io.delr3ves.restcheduler.RestchedulerConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.lifecycle.Managed;
import org.quartz.Scheduler;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
import org.quartz.utils.PoolingConnectionProvider;

import javax.inject.Inject;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class QuartzSchedulerManager implements Managed {

    private Scheduler scheduler;

    private RestchedulerConfiguration.QuartzConfiguration quartzConfiguration;

    @Inject
    public QuartzSchedulerManager(Scheduler scheduler, RestchedulerConfiguration.QuartzConfiguration quartzConfiguration) {
        this.scheduler = scheduler;
        this.quartzConfiguration = quartzConfiguration;
    }

    @Override
    public void start() throws Exception {
        try {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            DataSourceFactory dbConfig = quartzConfiguration.getDbParams();
            ConnectionProvider provider = new PoolingConnectionProvider(dbConfig.getDriverClass(), dbConfig.getUrl(), dbConfig.getUser(),
                    dbConfig.getPassword(), dbConfig.getMaxSize(), dbConfig.getValidationQuery());
            manager.addConnectionProvider(quartzConfiguration.getDatasource(), provider);
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws Exception {
        scheduler.shutdown(true);
    }

}
