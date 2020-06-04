package http.parsers;

import http.requests.RequestContext;

import java.util.List;

public class RequestParser {

    // TODO: 흠.. parser 치고는 일을 너무 편하게 하는데..? 필요한 객체일까 =_=
    public static RequestContext parse(String rawRequestLine, List<String> rawRequestHeaders) {
        return new RequestContext(rawRequestLine, rawRequestHeaders);
    }
}
