package io.delr3ves.restcheduler.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestJobDescription extends JobDescription {

    public static final String URL_FILED_NAME = "url";
    public static final String METHOD_FIELD_NAME = "method";
    public static final String CONTENT_TYPE_FIELD_NAME = "contentType";
    public static final String PAYLOAD_FIELD_NAME = "payload";

    @NotNull
    private String method;

    @URL
    @NotEmpty
    private String url;

    private String contentType;

    private String payload;

    @Override
    public Map<String, ? extends Serializable> getExtraParams() {
        return new HashMap<String, Serializable>() {{
            put(URL_FILED_NAME, url);
            put(METHOD_FIELD_NAME, method);
            put(CONTENT_TYPE_FIELD_NAME, contentType);
            put(PAYLOAD_FIELD_NAME, payload);
        }};
    }
}
