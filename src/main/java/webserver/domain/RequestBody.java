package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody {
    public static final String DELIMITER = " ";
    public static final String QUERY_DELIMITER = "\\?";
    public static final int PATH_INDEX = 1;
    public static final int QUERYSTRING_INDEX = 1;

    private final Parameters parameters = Parameters.newInstance();

    public RequestBody() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RequestBody(@JsonProperty("requestBody") String line) {
        this.parameters.addParameters(line);
    }

    public static RequestBody fromRequestLine(String line) {
        String path = line.split(DELIMITER)[PATH_INDEX];
        String[] pathValues = path.split(QUERY_DELIMITER);

        if (existsQueryString(pathValues)) {
            return new RequestBody(pathValues[QUERYSTRING_INDEX]);
        }
        return new RequestBody();
    }

    private static boolean existsQueryString(String[] queryArr) {
        return queryArr.length == 2;
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

    @Override
    public String toString() {
        return parameters.toString();
    }

    public Parameters getParameters() {
        return parameters;
    }
}
