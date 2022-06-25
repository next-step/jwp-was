package webserver.adapter.in.controller;

import webserver.adapter.in.HttpRequest;
import webserver.adapter.out.web.HttpResponse;

import java.io.IOException;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
