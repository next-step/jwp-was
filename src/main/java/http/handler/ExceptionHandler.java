package http.handler;

import http.HttpHeaders;
import http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
public class ExceptionHandler extends AbstractHandler {
    private final HttpStatus httpStatus;

    public ExceptionHandler(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    protected HttpHeaders getHttpHeaders(int length) {
        return new HttpHeaders(Collections.emptyMap());
    }
/*
    @Override
    public void writeResponse(OutputStream out, HttpResponse httpResponse) {

    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }*/
}
