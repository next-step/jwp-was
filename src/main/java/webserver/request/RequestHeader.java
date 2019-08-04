package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MapUtils;
import utils.StringUtils;
import webserver.RequestHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class RequestHeader {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private static final char HEADER_DELIMITER = ':';

    private String host;
    private String accept;
    private Map<String, String> headers;

    public RequestHeader(Map<String, String> headers) {
        this.headers = headers;
        this.host = headers.get("Host");
        this.accept = headers.get("Accept");
    }

    public static RequestHeader parse(List<String> requestHeaders) {
        logger.debug("## header: \n{}", requestHeaders.stream().collect(joining("\n")));
        Map<String, String> headerMap = new HashMap<>();
        for (String requestHeader : requestHeaders) {
            MapUtils.putIfKeyNotBlank(headerMap,
                    StringUtils.frontSplitWithOrigin(requestHeader, HEADER_DELIMITER),
                    StringUtils.endSplit(requestHeader, HEADER_DELIMITER));
        }

        return new RequestHeader(headerMap);
    }

    public String getHost() {
        return host;
    }

    public String getAccept() {
        return accept;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
