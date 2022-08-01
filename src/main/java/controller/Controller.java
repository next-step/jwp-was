package controller;

import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    boolean matchHttpMethodAndPath(HttpRequest request);

    HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException;
}
