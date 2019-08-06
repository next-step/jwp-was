package webserver;

import java.io.IOException;

public interface Response {

    void setCookie(String value);

    void redirect(String location) throws IOException;

    void ok(byte[] responseBody) throws IOException;

    void ok(byte[] responseBody, String contentType) throws IOException;

    void notFound() throws IOException;

    void internalServerError() throws IOException;
}