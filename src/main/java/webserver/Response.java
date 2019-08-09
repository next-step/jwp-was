package webserver;

import webserver.response.HeaderProperty;

import java.io.IOException;
import java.io.OutputStream;

public interface Response {

    void setCookie(String value);

    void send(OutputStream out) throws IOException;

    String getHeader(HeaderProperty key);
}