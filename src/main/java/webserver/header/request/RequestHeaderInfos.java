package webserver.header.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestHeaderInfos {
    private static final int REQUEST_LINE_NEXT_IDX = 1;
    private static final String HEADER_DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String DEFAULT_LENGTH = "0";
    private final Map<String, String> infos;

    public RequestHeaderInfos(String[] infos) {
        this.infos = Collections.unmodifiableMap(createHeaderInfo(infos));
    }

    public int contentsLength() {
        return Integer.parseInt(infos.getOrDefault(CONTENT_LENGTH, DEFAULT_LENGTH));
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
