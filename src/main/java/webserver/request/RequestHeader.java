package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MapUtils;
import utils.StringUtils;
import webserver.HttpParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class RequestHeader {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private static final char HEADER_DELIMITER = ':';

    private Map<String, String> headers;

    public RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader parse(List<String> requestHeaders) {
        logger.debug("## header: \n{}", String.join("\n", requestHeaders));
        Map<String, String> headerMap = new HashMap<>();
        for (String requestHeader : requestHeaders) {
            MapUtils.putIfKeyNotBlank(headerMap,
                    StringUtils.frontSplitWithOrigin(requestHeader, HEADER_DELIMITER),
                    StringUtils.endSplit(requestHeader, HEADER_DELIMITER));
        }

        return new RequestHeader(headerMap);
    }

    public String getHost() {
        return headers.get("Host");
    }

    public String getAccept() {
        return headers.get("Accept");
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }

    public Integer getContentLength() {
        return Integer.valueOf(headers.get("Content-Length"));
    }

    public String getCookie(String key) {
        String cookies = headers.get("Cookie");
        HttpParameter httpParameter = HttpParameter.parseParameter(cookies, ";");
        return httpParameter.getParameter(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
