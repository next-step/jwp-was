package webserver.http;

import webserver.http.enums.HTTPMethod;

public class RequestTestConstant {
    static final HTTPMethod GET_METHOD = HTTPMethod.GET;
    static final HTTPMethod POST_METHOD = HTTPMethod.POST;
    static final String PATH = "/users";
    static final String PROTOCOL = "HTTP";
    static final String VERSION = "1.1";
    static final String PROTOCOL_VERSION = "HTTP/1.1";
    static final String GET_REQUEST = "GET /users HTTP/1.1";
    static final String POST_REQUEST = "POST /users HTTP/1.1";
    static final String GET_REQUEST_QUERY_STRING = "GET /users?userId=test&password=password&name=test HTTP/1.1";
    static final String PATH_QUERY_STRING = "/users?userId=test&password=password&name=test";
    static final String QUERY_STRING = "userId=test&password=password&name=test";
    static final String INVALID_QUERY_STRING = "userId-test&password-password&name-test";
    static final String REQUEST_SPLIT_SYMBOL = " ";

}