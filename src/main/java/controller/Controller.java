package controller;

import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.RequestMappingInfo;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    HttpResponse process(HttpRequest request) throws Exception;
}
