package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {

    private String method;
    private String path;
    private String queryString;
    private Map<String, String> parameters;

    public RequestLine(String meth, String value1) {
        this.method = meth;
        String[] tokens = value1.split("\\?");
        this.path = tokens[0];
        if (tokens.length < 2) {
            return;
        }

        this.queryString = tokens[1];
        parameters = new HashMap<>();
        String[] parameterUnits = this.queryString.split("&");
        for (String unit : parameterUnits) {
            String[] keyAndValue = unit.split("=");
            parameters.put(keyAndValue[0], keyAndValue[1]);
        }
    }

    public static RequestLine parse(String s) {
        String[] values = s.split(" ");
        return new RequestLine(values[0], values[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
