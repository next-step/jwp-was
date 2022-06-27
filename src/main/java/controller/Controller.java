package controller;

import webserver.Request;
import webserver.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    Response service(Request request) throws IOException, URISyntaxException;
}
