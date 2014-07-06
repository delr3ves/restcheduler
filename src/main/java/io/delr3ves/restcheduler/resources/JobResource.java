package io.delr3ves.restcheduler.resources;

import com.codahale.metrics.annotation.Timed;
import io.delr3ves.restcheduler.api.Job;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Path("jobs")
public class JobResource {

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Timed
    public Job findJob(@PathParam("id") String id) {
        return new Job(id, "test");
    }
}
