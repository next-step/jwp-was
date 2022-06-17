package controller;

import java.io.IOException;
import webserver.request.Request;
import webserver.response.Response;

public interface Controller {
    Response service(Request request) throws IOException;
}
