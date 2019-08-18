package webserver.controller;

import webserver.http.request.HttpRequest;

import java.io.OutputStream;

public interface Controller {
    public void handle(OutputStream out, HttpRequest request);
}
