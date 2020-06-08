package http.response;

import http.HttpResponseCode;

public class HttpResponse400 extends HttpResponse {

    public HttpResponse400(String version) {
        super(HttpResponseCode.NOT_FOUNT, version);
    }
}
