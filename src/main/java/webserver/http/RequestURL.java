package webserver.http;

import java.util.Map;

public class RequestURL {

    private final static String PATH_SEPARATOR = "?";

    private String path;
    private Parameter parameter;

    private RequestURL(String uri) {
        this.path = uri;
        this.parameter = Parameter.empty();
    }

    private RequestURL(String uri, Parameter parameter) {
        this.path = uri;
        this.parameter = parameter;
    }

    public static RequestURL parse(String urlString) {
        String path = urlString;
        int q = urlString.lastIndexOf(PATH_SEPARATOR);

        if(q > -1) {
            path = urlString.substring(0, q);
            String querySting = urlString.substring(q + 1);
            Parameter parameter = Parameter.parse(querySting);

            return new RequestURL(path, parameter);
        }
        return new RequestURL(path);

    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameter.getParameters();
    }
    public String getParameter(String key) {
        return parameter.get(key);
    }
}
