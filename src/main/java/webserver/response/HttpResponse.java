package webserver.response;

import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {

    private ResponseLine responseLine;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeaders responseHeaders = ResponseHeaders.of();
        ResponseBody responseBody = ResponseBody.of(httpRequest.getRequestLine());
        responseHeaders.addHeader("Content-Type", responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader("Content-Length", responseBody.getLength());
        return new HttpResponse(responseHeaders, responseBody);
    }

    public static HttpResponse of() {
        ResponseHeaders responseHeaders = ResponseHeaders.of();
        ResponseBody responseBody = new ResponseBody();
        return new HttpResponse(responseHeaders, responseBody);
    }


    public byte[] getBody() {
        byte[] file = responseBody.getFile();
        if (Objects.isNull(file)) {
            return "".getBytes();
        }
        return file;
    }

    public void response200() {
        this.responseLine = ResponseLine.of("200", "OK");
    }

    public void response302(String location) {
        this.responseLine = ResponseLine.of("302", "FOUND");
        responseHeaders.addHeader("Location", location);
    }

    public Map<String, ResponseHeader> getResponseHeaders() {
        return responseHeaders.getResponseHeaders();
    }

    public String getResponseLine() {
        return responseLine.response();
    }
}
