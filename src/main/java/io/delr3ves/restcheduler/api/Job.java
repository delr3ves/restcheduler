package io.delr3ves.restcheduler.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    private String id;

    private String description;

}
