package webserver;

import webserver.http.Request;
import webserver.http.Response;

public interface Handler {

    RequestMappingInfo getMappingInfo();

    void handle(Request request, Response response);
}
