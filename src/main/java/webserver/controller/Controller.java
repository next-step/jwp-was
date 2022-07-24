package webserver.controller;

import http.request.RequestLine;

public interface Controller {
    void run(RequestLine requestLine);
}
