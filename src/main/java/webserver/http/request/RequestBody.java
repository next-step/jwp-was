package webserver.http.request;

import webserver.http.ContentType;
import webserver.http.HttpParameter;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBody {

    private HttpParameter httpParameter;

    public RequestBody(HttpParameter httpParameter) {
        this.httpParameter = httpParameter;
    }

    public static RequestBody from(BufferedReader reader, RequestHeader requestHeader) throws IOException {
        ContentType contentType = ContentType.getByType(requestHeader.getContentType());
        return new RequestBody(contentType.to(reader, requestHeader));
    }

    public HttpParameter getHttpParameter() {
        return this.httpParameter;
    }

}
