package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody {
    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RequestBody(@JsonProperty("requestBody") String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
