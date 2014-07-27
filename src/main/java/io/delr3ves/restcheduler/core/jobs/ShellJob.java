package io.delr3ves.restcheduler.core.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Slf4j
public class ShellJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(context.toString());
    }
}
