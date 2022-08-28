package webserver.http.domain;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final String HEADER_DELIMITER = ":";

    private final Map<String, String> headers = new HashMap<>();

    public void addRequestHeaders(String url, BufferedReader br) throws IOException {
        while (StringUtils.hasText(url)) {
            addHeader(url);
            url = br.readLine();
        }
    }

    private void addHeader(String header) {
        String[] split = header.split(HEADER_DELIMITER);
        this.headers.put(split[0], split[1].trim());
    }

    public String getValue(String name) {
        return headers.get(name).trim();
    }

}
