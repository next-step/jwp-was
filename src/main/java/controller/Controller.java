package controller;

import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    boolean match(HttpRequest request);

    HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException;
}
