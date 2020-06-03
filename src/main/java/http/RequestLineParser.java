package http;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String[] requestLineArray = requestLine.split(" ");

        String[] pathStrings = requestLineArray[1].split("\\?");
        String queryString = "";
        if(pathStrings.length == 2) {
            queryString = pathStrings[1];
        }

        return new RequestLine(
                getMethod(requestLineArray[0]),
                getPath(requestLineArray[1]),
                new Protocol(requestLineArray[2]),
                new QueryString(queryString));
    }

    public static HttpMethod getMethod(String methodString) {
        HttpMethod httpMethod = HttpMethod.valueOf(methodString);
        return httpMethod;
    }

    public static String getPath(String requestLine) {
        return requestLine.split("\\?")[0];
    }

    public static Map<String, String> getParameterMap(String path) {
        String queryString = path.split("\\?")[1];
        String[] parameters = queryString.split("&");

        Map<String, String> parameterMap = new LinkedHashMap<>();

        for (String parameter : parameters) {
            String[] parameterSplit = parameter.split("=");

            String key = parameterSplit[0];
            String value = parameterSplit[1];

            parameterMap.put(key, value);
        }

        return parameterMap;
    }
}
