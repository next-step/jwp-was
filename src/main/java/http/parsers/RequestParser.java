package http.parsers;

import http.requests.HttpRequest;

import java.util.List;

public class RequestParser {

    // TODO: remove me
    public static HttpRequest parse(String rawRequestLine, List<String> rawRequestHeaders) {
        return new HttpRequest(rawRequestLine, rawRequestHeaders, "");
    }
}
