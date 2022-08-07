package webserver.http;

import org.springframework.http.HttpStatus;

public class StatusLine {
    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;

    public StatusLine(HttpStatus httpStatus) {
        this.httpProtocol = HttpProtocol.HTTP_1_1;
        this.httpStatus = httpStatus;
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
