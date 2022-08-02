package controller;

import webserver.http.request.Request;
import webserver.http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) throws IOException, URISyntaxException {
        if (request.isPost()) {
            doPost(request, response);
        }

        if (request.isGet()) {
            doGet(request, response);
        }
    }

    public abstract void doPost(Request request, Response response);

    public abstract void doGet(Request request, Response response) throws IOException, URISyntaxException;
}
