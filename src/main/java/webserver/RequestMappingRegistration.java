package webserver;

import webserver.http.HttpMethod;

class RequestMappingRegistration {

    private final RequestMappingInfo requestMappingInfo;

    private final Handler handler;

    RequestMappingRegistration(String url, HttpMethod method, Handler handler) {
        this.requestMappingInfo = new RequestMappingInfo(url, method);
        this.handler = handler;
    }

    RequestMappingInfo getRequestMappingInfo() {
        return requestMappingInfo;
    }

    Handler getHandler() {
        return handler;
    }
}
