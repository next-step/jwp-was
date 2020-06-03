package http;

import java.util.Arrays;

public class QueryString {
    private final String queryString;

    public QueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getParameter(String name) {
        String parameterValue = "";
        String[] values = name.split("&");
        for (String value : values) {
            if(value.startsWith(name)) {
                String[] namesAndValue = value.split("=");
                parameterValue = namesAndValue[1];
                break;
            }
        }

        return parameterValue;
    }
}
