package webserver.http.dispatcher;

import webserver.http.request.Request;
import webserver.http.response.Response;

public interface Dispatcher<T extends Request, E extends Response> {
    void service(T request, E response);
}
