package http;

import http.Const.HttpConst;
import http.enums.Method;

public class RequestLineParser {
    public static RequestLine parse(String s) {
        String[] values = s.split(HttpConst.EMPTY_SPACE);
        return new RequestLine(Method.valueOf(values[0]), PathAndQuerySpliter.splitPath(values[1]), new Protocol(values[2]));
    }
}
