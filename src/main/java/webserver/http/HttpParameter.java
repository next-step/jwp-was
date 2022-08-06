package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpParameter {
    private Map<String, String> parameters = new HashMap<String, String>();

    public HttpParameter() {
    }

    public void loadParameters(String queryString) {
        String[] queryStringElements = queryString.split("&");
        for (String queryElement : queryStringElements) {
            String parameter = queryElement.split("=")[0];
            String value = queryElement.split("=")[1];
            parameters.put(parameter,value);
        }
    }

    public String getParameters(String name) {
        return parameters.get(name);
    }
}
