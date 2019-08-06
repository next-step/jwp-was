package controller;

import webserver.Controller;
import webserver.Request;
import webserver.Response;
import webserver.request.HttpMethod;

public abstract class AbstractController implements Controller {

    abstract void doGet(Request request, Response response) throws Exception;

    abstract void doPost(Request request, Response response) throws Exception;

    @Override
    public void service(Request request, Response response) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }
}
