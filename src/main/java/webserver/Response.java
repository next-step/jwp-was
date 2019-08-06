package webserver;

import webserver.response.HeaderProperty;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;

public interface Response {

    HttpStatus getStatus();

    String getHeaderOf(String key);

    String getHeader(HeaderProperty headerName);

    String getHeader(String headerName);

    void addHeader(HeaderProperty key, String value);

    void setCookie(String value);

    void send(OutputStream out) throws IOException;
}