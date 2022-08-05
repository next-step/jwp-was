package webserver.http.request.header;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestHeaderInfos {
    private static final String BLANK = " ";
    private static final int REQUEST_LINE_NEXT_IDX = 1;
    private static final String HEADER_DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String DEFAULT_LENGTH = "0";
    private static final String COOKIE = "Cookie";
    private static final String VALUE_DELIMITER = "=";
    private static final String SESSION_ID = "sessionId";
    private static final int VALUE_INDEX = 1;
    private static final int KEY_INDEX = 0;
    private static final String DEFAULT_UUID = "";
    private final Map<String, String> infos;

    RequestHeaderInfos(String[] infos) {
        this.infos = Collections.unmodifiableMap(createHeaderInfo(infos));
    }

    int contentsLength() {
        return Integer.parseInt(infos.getOrDefault(CONTENT_LENGTH, DEFAULT_LENGTH));
    }

    String sessionId() {
        if (!infos.containsKey(COOKIE)) {
            return DEFAULT_UUID;
        }

        return Arrays.stream(infos.get(COOKIE).split(BLANK))
                .filter(cookie -> cookie.startsWith(SESSION_ID))
                .findAny()
                .orElse(DEFAULT_UUID)
                .split(VALUE_DELIMITER)[VALUE_INDEX];
    }

    private static Map<String, String> createHeaderInfo(String[] headerInfos) {
        Map<String, String> result = new HashMap<>();
        for (int i = REQUEST_LINE_NEXT_IDX; i < headerInfos.length; i++) {
            String[] keyValue = headerInfos[i].split(HEADER_DELIMITER);
            result.put(keyValue[KEY_INDEX], keyValue[VALUE_INDEX]);
        }
        return result;
    }
}
