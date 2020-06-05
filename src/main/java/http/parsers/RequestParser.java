package http.parsers;

import http.requests.RequestContext;

import java.util.List;

public class RequestParser {

    // TODO: remove me
    public static RequestContext parse(String rawRequestLine, List<String> rawRequestHeaders) {
        return new RequestContext(rawRequestLine, rawRequestHeaders, "");
    }
}
