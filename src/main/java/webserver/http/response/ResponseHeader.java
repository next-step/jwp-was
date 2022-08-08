package webserver.http.response;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {

    private static final String CONNECTION = "Connection";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "location";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String SET_COOKIE = "Set-Cookie";

    private static final String HEADER_DELIMITER = ";";
    private static final String CHARSET = "charset=UTF-8";

    private static final Map<String, String> BASE_HEADERS = Map.of(
//            CONNECTION, "Keep-Alive",
            CONTENT_TYPE,  ContentType.HTML.type + HEADER_DELIMITER + CHARSET
    );

    private Map<String, String> headers = new HashMap<>();

    public ResponseHeader() {}

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static ResponseHeader baseResponseHeader(){
        return new ResponseHeader(Maps.newHashMap(BASE_HEADERS));
    }

    public String getLocation(){
        return headers.get(LOCATION);
    }

    public String getContentType() { return headers.get(CONTENT_TYPE); }

    public void setLocation(String redirectUrl) {
        this.headers.put(LOCATION, "http://localhost:8888" + redirectUrl);
    }

    public void setContentLength(String value) {
        this.headers.put(CONTENT_LENGTH, value);
    }

    public void setCookie(String cookie) {
        this.headers.put(SET_COOKIE, cookie);
    }

    public void setContentType(ContentType contentType) {
        this.headers.put(CONTENT_TYPE, contentType.type +  HEADER_DELIMITER + CHARSET);
    }

    public List<String> toPrint() {
        return headers.keySet().stream()
                .map(key -> key + ": " + headers.get(key) + "\r\n")
                .collect(Collectors.toList());
    }
}
