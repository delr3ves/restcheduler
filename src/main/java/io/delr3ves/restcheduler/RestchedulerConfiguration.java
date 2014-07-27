package io.delr3ves.restcheduler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;
import org.quartz.impl.jdbcjobstore.JobStoreTX;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Data
public class RestchedulerConfiguration extends Configuration {
    @Valid
    @JsonProperty
    private QuartzConfiguration quartzSettings = new QuartzConfiguration();

    public DataSourceFactory getQuartzDB() {
        return quartzSettings.getDbParams();
    }

    public Properties getSchedulerFactoryProperties() {
        Properties schedulerProperties = new Properties();
        // Quartz settings configured in YML file
        schedulerProperties.setProperty("or g.quartz.scheduler.instanceName", quartzSettings.getInstanceName());
        schedulerProperties.setProperty("org.quartz.threadPool.class", quartzSettings.getThreadPoolClass());
        schedulerProperties.setProperty("org.quartz.threadPool.threadCount", quartzSettings.getThreadCount().toString());
        schedulerProperties.setProperty("org.quartz.threadPool.threadPriority", quartzSettings.getThreadPriority().toString());
        schedulerProperties.setProperty("org.quartz.jobStore.class", JobStoreTX.class.getName());

        String datasource = quartzSettings.getDatasource();
        DataSourceFactory quartzDB = quartzSettings.getDbParams();
        schedulerProperties.setProperty("org.quartz.jobStore.driverDelegateClass", quartzSettings.getDriverDelegateClass());
        schedulerProperties.setProperty("org.quartz.jobStore.dataSource", datasource);
        schedulerProperties.setProperty("org.quartz.datasource." + datasource + ".driver", quartzDB.getDriverClass());
        schedulerProperties.setProperty("org.quartz.datasource." + datasource + ".URL", quartzDB.getUrl());
        schedulerProperties.setProperty("org.quartz.datasource." + datasource + ".user", quartzDB.getUser());
        schedulerProperties.setProperty("org.quartz.datasource." + datasource + ".password", quartzDB.getPassword());
        schedulerProperties.setProperty("org.quartz.datasource." + datasource + ".maxConnections", "5");
        schedulerProperties.setProperty("org.quartz.datasource." + datasource + ".validationQuery", quartzDB.getValidationQuery());
        return schedulerProperties;
    }

    @Data
    public class QuartzConfiguration {
        private String instanceName = "Restcheduler";
        private String threadPoolClass = "org.quartz.simpl.SimpleThreadPool";
        private String driverDelegateClass = "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate";
        private Integer threadCount = 5;
        private Integer threadPriority = 5;
        private String datasource = "quartzDS";
        private DataSourceFactory dbParams;

    }
}

