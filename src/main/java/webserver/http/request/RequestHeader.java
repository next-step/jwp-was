package webserver.http.request;

import exception.InvalidHeaderException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class RequestHeader {

    private static final String REQUEST_HEADER_DELIMITER = ": ";
    private static final String USER_AGENT = "User-Agent";
    private static final String ACCEPT = "Accept";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String HOST = "Host";
    private static final String CONNECTION = "Connection";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";

    private static final String EMPTY_VALUE = "";

    private Map<String, String> headers = new HashMap<>();

    public RequestHeader() {}

    public static RequestHeader parseFrom(BufferedReader br) throws IOException {
        final RequestHeader requestHeader = new RequestHeader();

        IOUtils.readLines(br)
                .forEach(string -> requestHeader.add(string));

        return requestHeader;
    }

    public void add (final String header){
        if (header == null || header.isEmpty() || header.isBlank()) {
            return;
        }

        String[] splitHeader = header.split(REQUEST_HEADER_DELIMITER);
        if (splitHeader.length != 2) {
            throw new InvalidHeaderException();
        }

        headers.put(splitHeader[0], splitHeader[1]);
    }

    public boolean hasBody() {
        return this.getContentLength().isEmpty();
    }

    public String getUserAgent() {
        return headers.get(USER_AGENT);
    }

    public String getAccept() {
        return headers.get(ACCEPT);
    }

    public String getCacheControl() {
        return headers.get(CACHE_CONTROL);
    }

    public String getHost() {
        return headers.get(HOST);
    }

    public String getConnection() {
        return headers.get(CONNECTION);
    }

    public Optional<Integer> getContentLength() {
        if (headers.containsKey(CONTENT_LENGTH)) {
            return Optional.of(parseInt(headers.get(CONTENT_LENGTH)));
        }
        return Optional.empty();
    }

    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }

    public Cookie getCookie() {
        if(!headers.containsKey(COOKIE)) {
            return Cookie.emptyCookie();
        }
        return Cookie.parseFrom(headers.get(COOKIE));
    }
}
