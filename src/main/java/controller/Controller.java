package controller;

import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.RequestMappingInfo;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    boolean isPath(RequestMappingInfo info);
    HttpResponse process(HttpRequest request) throws IOException, URISyntaxException;
}
