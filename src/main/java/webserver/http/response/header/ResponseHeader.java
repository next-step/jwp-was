package webserver.http.response.header;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import webserver.http.response.HttpResponseStatus;

public class ResponseHeader {
    public static final String BLANK = " ";
    public static final String PROTOCOL_STATUS = "protocolStatus";
    public static final String INFO_DELIMITER = ": ";
    private static final String SET_COOKIE = "Set-Cookie";
    public static final String ROOT_PATH = "Path=/";

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";
    private final Map<String, String> infos = new HashMap<>();

    public ResponseHeader(String protocolVersion, HttpResponseStatus httpResponseStatus) {
        infos.put(PROTOCOL_STATUS, protocolVersion + BLANK + httpResponseStatus.toResponseHeader());
    }

    public ResponseHeader addContentType(ContentType contentType) {
        infos.put(CONTENT_TYPE, contentType.getContent());
        return this;
    }

    public ResponseHeader addContentLength(int length) {
        infos.put(CONTENT_LENGTH, Integer.toString(length));
        return this;
    }

    public ResponseHeader addLocation(String location) {
        infos.put(LOCATION, location);
        return this;
    }

    public ResponseHeader addCookie(String cookie) {
        infos.put(SET_COOKIE, cookie + BLANK + ROOT_PATH);
        return this;
    }

    @Override
    public String toString() {
        return infos.get(PROTOCOL_STATUS) + "\r\n" + infos.entrySet().stream()
                .filter(info -> !info.getKey().equals(PROTOCOL_STATUS))
                .map(info -> info.getKey() + INFO_DELIMITER + info.getValue() + "\r\n")
                .collect(Collectors.joining()) + "\r\n";
    }
}
