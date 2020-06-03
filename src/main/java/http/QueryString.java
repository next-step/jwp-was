package http;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryString {

    private Map<String, String> paramterMap;

    public QueryString(String queryString) {
        if("".equals(queryString)) {
            paramterMap = new LinkedHashMap<>();
            return;
        }

        String[] parameters = queryString.split("&");

        Map<String, String> parameterMap = new LinkedHashMap<>();

        for (String parameter : parameters) {
            String[] parameterSplit = parameter.split("=");

            String key = parameterSplit[0];
            String value = parameterSplit[1];

            parameterMap.put(key, value);
        }

        this.paramterMap = parameterMap;
    }

    public String getParameter(String key) {
        return paramterMap.get(key);
    }

    public Map<String, String> getParameterMap() {
        return paramterMap;
    }
}
