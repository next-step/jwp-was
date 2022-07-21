package webserver.http.request.parser;

import webserver.http.request.Method;

public class MethodParser {

    public Method parse(String message) {
        return Method.from(message);
    }
}
