package webserver.http.response;

import webserver.view.View;

import java.io.IOException;

public interface Response {
    void writeHeader(String contentType, int contentLength) throws IOException;
    void redirect(String location, boolean logined) throws IOException;
    void writeBody(byte[] body) throws IOException;
    void writeBody(View view) throws IOException;
}
