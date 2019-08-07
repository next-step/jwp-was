package webserver.http.response;

import webserver.http.HttpStatus;

import java.io.IOException;

public interface Response {
    void writeHeader(HttpStatus httpStatus, String contentType, int contentLength) throws IOException;
    void redirect(String location) throws IOException;
    void writeBody(byte[] body) throws IOException;
}
