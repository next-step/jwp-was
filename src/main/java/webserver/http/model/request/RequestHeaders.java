package webserver.http.model.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestHeaders {
    private Map<String, String> requestHeadersMap;

    public RequestHeaders(BufferedReader bufferedReader, String line) throws IOException {
        initial(requestHeaderText(bufferedReader, line));
    }

    public RequestHeaders(String[] requestHeadersArray) {
        initializeArray(requestHeadersArray);
    }

    public RequestHeaders(List<String> requestHeadersArray) {
        initializeList(requestHeadersArray);
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

    private void initializeList(List<String> requestHeadersList) {
        requestHeadersMap = requestHeadersList.stream()
                .filter(header -> !"".equals(header))
                .collect(Collectors.toMap(header -> header.split(":")[0].trim(), header -> header.split(":")[1].trim()));
    }

    private String requestHeaderText(BufferedReader bufferedReader, String line) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (!"".equals(line)) {
            if (line == null) {
                break;
            }
            line = bufferedReader.readLine();
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    public Map<String, String> getRequestHeadersMap() {
        return requestHeadersMap;
    }
}
