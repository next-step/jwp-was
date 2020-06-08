package http.response;

import http.HttpResponseCode;

public class HttpResponse200 extends HttpResponse {

    public HttpResponse200(String version) {
        super(HttpResponseCode.OK, version);
    }
}
