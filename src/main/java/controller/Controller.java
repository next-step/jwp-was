package controller;

import model.HttpRequest;
import model.HttpResponse;

public interface Controller {
    boolean matchHttpMethodAndPath(HttpRequest request);

    HttpResponse execute(HttpRequest request);
}
