package webserver.request;

import utils.MapUtils;
import utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {

    private static char HEADER_DELIMITER = ':';

    private String host;
    private Map<String, String> headers;

    public RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader parse(List<String> requestHeaders) {
        Map<String, String> headerMap = new HashMap<>();
        for (String requestHeader : requestHeaders) {
            MapUtils.putIfKeyNotBlank(headerMap,
                    StringUtils.frontSplitWithOrigin(requestHeader, HEADER_DELIMITER),
                    StringUtils.endSplit(requestHeader, HEADER_DELIMITER));
        }

        return new RequestHeader(headerMap);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
