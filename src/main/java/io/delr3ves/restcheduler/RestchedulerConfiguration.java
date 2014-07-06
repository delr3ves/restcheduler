package io.delr3ves.restcheduler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class RestchedulerConfiguration extends Configuration {
    @Valid
    @JsonProperty
    private Map<String,String> quartzSettings = new HashMap<String, String>();

    public String getSchedulerName() {
        return quartzSettings.get("instanceName");
    }

    public Properties getSchedulerFactoryProperties() {
        Properties schedulerProperties = new Properties();
        // Fixed Quartz settings. They could easily be added to the YAML config file
        schedulerProperties.setProperty("org.quartz.plugin.jobInitializer.class", "org.quartz.plugins.xml.XMLSchedulingDataProcessorPlugin");
        schedulerProperties.setProperty("org.quartz.plugin.jobInitializer.failOnFileNotFound", "true");
        // Job XML files can be changed without restarting the service by being check at regular intervals
        schedulerProperties.setProperty("org.quartz.plugin.jobInitializer.scanInterval", "0");
        schedulerProperties.setProperty("org.quartz.plugin.jobInitializer.wrapInUserTransaction", "false");
        // Quartz checks for updates. This should be turned off for production systems.
        schedulerProperties.setProperty("org.quartz.scheduler.skipUpdateCheck","true");

        // Quartz settings configured in YML file
        schedulerProperties.setProperty("org.quartz.scheduler.instanceName", quartzSettings.get("instanceName"));
        schedulerProperties.setProperty("org.quartz.threadPool.class", quartzSettings.get("threadPoolClass"));
        schedulerProperties.setProperty("org.quartz.threadPool.threadCount", quartzSettings.get("threadCount"));
        schedulerProperties.setProperty("org.quartz.threadPool.threadPriority", quartzSettings.get("threadPriority"));
        schedulerProperties.setProperty("org.quartz.jobStore.class", quartzSettings.get("jobStoreClass"));
        return schedulerProperties;
    }
}
