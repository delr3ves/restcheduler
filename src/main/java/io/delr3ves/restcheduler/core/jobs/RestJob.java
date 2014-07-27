package io.delr3ves.restcheduler.core.jobs;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.RequestBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Slf4j
public class RestJob implements Job {

    private Client restClient;

    public RestJob() {
        this.restClient = new Client();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String url = (String) context.get("url");
        String method = (String) context.get("method");
        String contentType = (String) context.get("contentType");
        String payload = (String) context.get("payload");

        WebResource resource = restClient.resource(url);
        if (contentType != null) {
            resource.header("Content-Type", contentType);
        }
        try {
            resource.method(method, payload);
            log.debug("Executed Job " + context.getJobDetail().getKey() + "with description " + context.getJobDetail().getDescription());
        } catch (RuntimeException e) {
            throw new JobExecutionException(e);
        }
    }
}
