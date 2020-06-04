package http;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpHeader {
    private static final String LINE_REGEX = ": ";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String ACCEPT_HEADER_REGEX = ",";

    private Map<String, String> headerMap;

    private HttpHeader(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static HttpHeader of(BufferedReader br) throws IOException {
        Map<String, String> headerMap = new HashMap<>();

        String line;
        while ((line = br.readLine()) == null || !line.equals("")) {
            String[] headerValues = line.split(LINE_REGEX);
            headerMap.put(headerValues[0], headerValues[1].trim());
        }
        return new HttpHeader(headerMap);
    }

    public String getValue(String key) {
        return headerMap.get(key);
    }

    public String getContentType() {
        String[] acceptValues = getValue(ACCEPT_HEADER).split(ACCEPT_HEADER_REGEX);
        return acceptValues[0];
    }
}
