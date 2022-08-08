package mvc.controller;

import http.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String method = request.getHttpMethod();

        if (method.equals(HttpMethod.GET.toString())) {
            doGet(request, response);
        }
        if (method.equals(HttpMethod.POST.toString())) {
            doPost(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
    }
}
