package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestLineParser {
    private final String method;
    private final String path;
    private final String protocol;
    private final String version;
    private final Map<String, String> queryString;

    public RequestLineParser(String requestLine) {
        String[] requestLineElements = requestLine.split(" ");
        this.method = requestLineElements[0];
        if (!requestLineElements[1].contains("?")) {
            this.path = requestLineElements[1];
            this.queryString = null;
        }
        else {
            this.path = requestLineElements[1].split("\\?")[0];
            this.queryString = queryStringParser(requestLineElements[1].split("\\?")[1]);
        }
        this.protocol = requestLineElements[2].split("/")[0];
        this.version = requestLineElements[2].split("/")[1];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public Map<String,String> queryStringParser(String queryString) {
        String[] queryStringElements = queryString.split("&");
        HashMap<String, String> querySet = new HashMap<String, String>();
        for (String queryElement : queryStringElements) {
            String parameter = queryElement.split("=")[0];
            String value = queryElement.split("=")[1];
            querySet.put(parameter,value);
        }
        return querySet;
    }
}
