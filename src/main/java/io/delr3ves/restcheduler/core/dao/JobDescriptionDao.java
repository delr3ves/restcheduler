package io.delr3ves.restcheduler.core.dao;

import io.delr3ves.restcheduler.core.model.JobDescription;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class JobDescriptionDao extends AbstractDAO<JobDescription> {

    @Inject
    public JobDescriptionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public JobDescription findById(Long id) {
        return get(id);
    }

}
