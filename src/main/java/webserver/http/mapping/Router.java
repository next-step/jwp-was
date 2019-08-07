package webserver.http.mapping;

import webserver.http.request.Request;
import webserver.http.response.Response;


public interface Router <T extends Request, E extends Response> {
    void route(T request, E response);
}
