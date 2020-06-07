package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Handler {

    HttpResponse handle(HttpRequest httpRequest);
}
