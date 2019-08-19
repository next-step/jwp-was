package webserver.http.request;

import utils.HttpStringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class HttpRequestHeader {
    private Map<String, String> requestHeader;

    private HttpRequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public static HttpRequestHeader of(BufferedReader br) throws IOException {
        Map<String, String> requestHeader = new HashMap<>();
        String line;

        while (!"".equals(line = br.readLine())) {
            if (line == null) {
                break;
            }
            String[] headerData = HttpStringUtils.split(line, ": ");
            requestHeader.put(headerData[0], headerData[1]);
        }

        return new HttpRequestHeader(requestHeader);
    }

    public String findByKey(String key) {
        return Optional.ofNullable(requestHeader.get(key))
                .orElseThrow(NoSuchElementException::new);
    }

    public int getContentLength() {
        return Integer.parseInt(requestHeader.get("Content-Length"));
    }

    public Set<String> getKeys() {
        return requestHeader.keySet();
    }
}
