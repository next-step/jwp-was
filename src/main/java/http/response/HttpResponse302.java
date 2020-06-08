package http.response;

import http.HttpResponseCode;

public class HttpResponse302 extends HttpResponse {

    public HttpResponse302(String path, String version) {
        super(HttpResponseCode.FOUND, version);
        this.addHeader("Location", path);
    }
}
