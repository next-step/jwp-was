package webserver.http.request;

import webserver.http.HttpParameter;

public class RequestBody {

    private HttpParameter httpParameter;

    public RequestBody(HttpParameter httpParameter) {
        this.httpParameter = httpParameter;
    }

    public HttpParameter getHttpParameter() {
        return this.httpParameter;
    }

}
