package webserver.http.domain;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final String HEADER_DELIMITER = ":";

    private final Map<String, String> headers = new HashMap<>();

    public void addRequestHeaders(BufferedReader br) throws IOException {
        String header = br.readLine();
        while (StringUtils.hasText(header)) {
            addHeader(header);
            header = br.readLine();
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
