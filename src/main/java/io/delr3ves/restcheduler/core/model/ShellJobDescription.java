package io.delr3ves.restcheduler.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio Arroyo - @delr3ves
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShellJobDescription extends JobDescription {

    private String command;

    @Override
    public Map<String, ? extends Serializable> getExtraParams() {
        return new HashMap<String, Serializable>();
    }
}
