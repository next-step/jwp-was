package webserver.http.model;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RequestBody {
    private static final RequestBody emptyBody = new RequestBody(Collections.emptyMap());
    private Map<String, String> requestBodyMap;

    public RequestBody (BufferedReader bufferedReader, RequestHeaders requestHeaders) throws IOException {
        String contentLength = requestHeaders.getRequestHeadersMap().get("Content-Length");
        String requestBodyText = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        initial(requestBodyText);
    }

    public RequestBody(Map<String, String> requestBodyMap) {
        this.requestBodyMap = requestBodyMap;
    }

    public RequestBody(List<String> requestBodyList) {
        initialList(requestBodyList);
    }

    public RequestBody(String requestBodyText) {
        initial(requestBodyText);
    }

    public static RequestBody empty() {
        return emptyBody;
    }

    private void initial(String requestBodyText) {
        String[] requestBodyTexts = requestBodyText.split("&");
        requestBodyMap = Arrays.stream(requestBodyTexts)
                .collect(Collectors.toMap(text -> text.split("=")[0].trim(), text -> text.split("=")[1].trim()));
    }

    private void initialList(List<String> requestBodyList) {
        requestBodyMap = requestBodyList.stream()
                .collect(Collectors.toMap(text -> text.split("=")[0].trim(), text -> text.split("=")[1].trim()));
    }

    public Map<String, String> getRequestBodyMap() {
        return requestBodyMap;
    }
}
