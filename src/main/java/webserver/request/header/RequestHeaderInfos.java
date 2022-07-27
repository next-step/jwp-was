package webserver.request.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestHeaderInfos {
    private static final int REQUEST_LINE_NEXT_IDX = 1;
    private static final String HEADER_DELIMITER = ": ";
    private static final String HOST = "Host";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String DEFAULT_LENGTH = "0";
    public static final String DEFAULT_VALUE = "";
    public static final String COOKIE = "Cookie";
    private final Map<String, String> infos;

    RequestHeaderInfos(String[] infos) {
        this.infos = Collections.unmodifiableMap(createHeaderInfo(infos));
    }

    String host() {
        return infos.getOrDefault(HOST, DEFAULT_VALUE);
    }

    int contentsLength() {
        return Integer.parseInt(infos.getOrDefault(CONTENT_LENGTH, DEFAULT_LENGTH));
    }

    public String cookie() {
        return infos.getOrDefault(COOKIE, DEFAULT_VALUE);
    }

    private static Map<String, String> createHeaderInfo(String[] headerInfos) {
        Map<String, String> result = new HashMap<>();
        for (int i = REQUEST_LINE_NEXT_IDX; i < headerInfos.length; i++) {
            String[] keyValue = headerInfos[i].split(HEADER_DELIMITER);
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }
}
