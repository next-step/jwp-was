package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MapUtils;
import utils.StringUtils;
import webserver.http.ContentType;
import webserver.http.Cookies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private static final char HEADER_DELIMITER = ':';
    private static final String COOKIE = "Cookie";

    private Map<String, String> headers;
    private Cookies cookies;

    private RequestHeader(Map<String, String> headers, Cookies cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public static RequestHeader from(List<String> requestHeaders) {
        logger.debug("## header: \n{}", String.join("\n", requestHeaders));
        Map<String, String> headers = new HashMap<>();
        for (String requestHeader : requestHeaders) {
            MapUtils.putIfKeyNotBlank(headers,
                    StringUtils.frontSplitWithOrigin(requestHeader, HEADER_DELIMITER),
                    StringUtils.endSplit(requestHeader, HEADER_DELIMITER));
        }
        return new RequestHeader(headers, Cookies.parse(headers.get(COOKIE)));
    }

    public String getAccept() {
        return headers.get("Accept");
    }

    public ContentType getContentType() {
        return ContentType.getByType(headers.get("Content-Type"));
    }

    public Integer getContentLength() {
        return Integer.valueOf(headers.getOrDefault("Content-Length", "0"));
    }

    public String getCookie(String key) {
        return cookies.getCookie(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
