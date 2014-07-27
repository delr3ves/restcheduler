package io.delr3ves.restcheduler.core.model;

import com.fasterxml.jackson.annotation.*;
import io.dropwizard.validation.ValidationMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestJobDescription.class, name = JobDescription.REST_TYPE),
        @JsonSubTypes.Type(value = ShellJobDescription.class, name = JobDescription.SHELL_TYPE),
})
public abstract class JobDescription implements Serializable{

    public static final Integer NO_REPEAT_JOB = 1;
    public static final Integer DEFAULT_RETRIES = 5;
    public static final Long DEFAULT_TIME_BETWEEN_RETRIES = 1000l;
    public static final String REST_TYPE = "rest";
    public static final String SHELL_TYPE = "shell";

    @Id
    private String id;

    private String description;

    private DateTime executionDateUTC;

    private String cronExpression;

    private Integer maxRetries = DEFAULT_RETRIES;

    private String type;

    /**
     * < 1 means infinite
     */
    private Integer numberOfExecutions = NO_REPEAT_JOB;

    private Long executionsIntervalInMillis = DEFAULT_TIME_BETWEEN_RETRIES;

    @JsonIgnore
    public abstract Map<String, ? extends  Serializable> getExtraParams();

    @AssertTrue(message = "The job has no scheduling information")
    public Boolean isValidScheduingInfo() {
        return executionDateUTC != null || cronExpression != null;
    }

    @AssertTrue(message = "The job has scheduled to be executed in the past")
    public Boolean isScheduledForFuture() {
        Boolean excheduledInFuture = true;
        if (executionDateUTC != null) {
            excheduledInFuture = executionDateUTC.isAfter(new DateTime());
        }
        return excheduledInFuture;
    }
}
