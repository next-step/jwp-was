package controller;

import webserver.Controller;
import webserver.Request;
import webserver.Response;
import webserver.request.HttpMethod;
import webserver.response.HttpResponse;

public abstract class AbstractController implements Controller {

    abstract Response doGet(Request request) throws Exception;

    abstract Response doPost(Request request) throws Exception;

    @Override
    public Response service(Request request) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET)) {
            return doGet(request);
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            return doPost(request);
        }
        return HttpResponse.notFound();
    }
}
