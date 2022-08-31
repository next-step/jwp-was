package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import model.request.HttpRequest;
import model.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
