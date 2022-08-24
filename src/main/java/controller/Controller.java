package controller;

import java.io.IOException;
import model.request.HttpRequest;
import model.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws IOException;
}
