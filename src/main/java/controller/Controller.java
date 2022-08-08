package controller;

import webserver.http.request.Request;
import webserver.http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    void service(Request request, Response response) throws IOException, URISyntaxException;
}
