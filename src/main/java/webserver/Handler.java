package webserver;

import webserver.http.Request;
import webserver.http.Response;

public interface Handler {

    boolean isSupport(Request request);

    void handle(Request request, Response response);
}
