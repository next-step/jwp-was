package webserver.session;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Interceptor {

    boolean preHandle(HttpRequest request, HttpResponse response);
}
