package controller;

import webserver.http.request.Request;
import webserver.http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    public static final String ROOT_FILE = "/index.html";

    @Override
    public void service(Request request, Response response) throws IOException, URISyntaxException {
        if (request.isPost()) {
            doPost(request, response);
        }
        
        if (request.isGet()) {
            doGet(request, response);
        }
    }

    public void doPost(Request request, Response response) {
        response.sendRedirect(ROOT_FILE);
    }

    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.forward(request.getRequestPath());
    }
}
