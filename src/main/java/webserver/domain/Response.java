package webserver.domain;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Response {
    private final HttpStatus httpStatus;
    private final Object body;

    private final HttpHeaders headers;

    public Response(String body) {
        this(HttpStatus.OK, body, HttpHeaders.defaultResponseHeader());
    }

    public Response(HttpStatus httpStatus, Object body) {
        this(httpStatus, body, HttpHeaders.defaultResponseHeader());
    }

    public Response(HttpStatus httpStatus, Object body, HttpHeaders headers) {
        this.httpStatus = httpStatus;
        this.body = body;
        this.headers = headers;
    }

    public String getHttpStatusMessage() {
        return httpStatus.value();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Object getBody() {
        return body;
    }

    public String getTemplate() throws IOException, URISyntaxException {
        return new String(FileIoUtils.loadFileFromClasspath("./templates/" + body));

    }
}
