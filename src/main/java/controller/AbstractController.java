package controller;

import model.request.HttpRequest;
import model.response.HttpResponse;
import utils.HttpMethod;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        ;
        if (request.getRequestLine().getMethod() == HttpMethod.GET) {
            doGet(request, response);

            return;
        }
        doPost(request, response);
    }

    public void doPost(HttpRequest request, HttpResponse response) {

    }

    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
