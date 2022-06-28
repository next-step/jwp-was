package controller;

import webserver.Request;
import webserver.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RequestController {
    Response doGet(Request request) throws IOException, URISyntaxException;

    Response doPost(Request request) throws IOException, URISyntaxException;
}
