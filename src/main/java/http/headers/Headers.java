package http.headers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Headers {
    private static final Logger logger = LoggerFactory.getLogger(Headers.class);

    private Map<String, String> headers = new HashMap<>();

    public Headers(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line)) {
            if (line == null) {
                return;
            }
            line = br.readLine();
            logger.debug("header : {}", line);
            String[] split = line.split(": ");
            if (split.length == 2) {
                header.put(split[0], split[1]);
            }
        }
        this.headers = Collections.unmodifiableMap(header);
    }

    public Headers(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getValue(String key) {
        return headers.get(key);
    }
}