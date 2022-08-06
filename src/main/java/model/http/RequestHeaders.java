package model.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestHeaders {
    private Map<String, String> requestHeadersMap;


    public RequestHeaders(String[] requestHeadersArray) {
        initializeArray(requestHeadersArray);
    }

    public RequestHeaders(String requestHeadersText) {
        initial(requestHeadersText);
    }

    private void initial(String requestHeadersText) {
        initializeArray(requestHeadersText.split("\n"));
    }

    private void initializeArray(String[] requestHeadersArray) {
        requestHeadersMap = Arrays.stream(requestHeadersArray)
                .collect(Collectors.toMap(header -> header.split(":")[0].trim(), header -> header.split(":")[1].trim()));
    }

    public Map<String, String> getRequestHeadersMap() {
        return requestHeadersMap;
    }
}
