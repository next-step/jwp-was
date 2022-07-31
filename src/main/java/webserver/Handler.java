package webserver;

import webserver.http.Request;
import webserver.http.Response;

public interface Handler {
    void handle(Request request, Response response);
}
