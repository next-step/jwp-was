package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody {
    public static final String DELIMITER = " ";
    public static final String QUERY_DELIMITER = "\\?";
    private final Parameters parameters = Parameters.newInstance();

    public RequestBody() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RequestBody(@JsonProperty("requestBody") String line) {
        this.parameters.addParameters(line);
    }

    public static RequestBody fromRequestLine(String line) {
        String path = line.split(DELIMITER)[1];
        String[] pathValues = path.split(QUERY_DELIMITER);

        if (pathValues.length == 2) {
            return new RequestBody(pathValues[1]);
        }
        return new RequestBody();
    }

    public void addAttributes(String line) {
        this.parameters.addParameters(line);
    }

    public String getAttribute(String key) {
        return parameters.get(key);
    }

    public void addAttribute(String key, String value) {
        parameters.addParameters(key, value);
    }
}
