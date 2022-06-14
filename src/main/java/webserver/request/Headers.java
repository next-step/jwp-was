package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class Headers {

    private final Map<String, String> headerMap;

    private Headers(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public Headers() {
        this(new HashMap<>());
    }

    public void addHeaderByLine(String line) {
        String[] split = line.split(": ", 2);
        if (split.length < 2) {
            return;
        }

        headerMap.put(split[0], split[1]);
    }

    public String get(String s) {
        return headerMap.get(s);
    }

    public int getContentLength() {
        String contentLength = headerMap.get("Content-Length");
        try {
            return Integer.parseInt(contentLength);
        } catch (Exception e) {
        }
        return 0;
    }
}
