package http.controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    public abstract void doGet(HttpRequest request, HttpResponse response);

    public abstract void doPost(HttpRequest request, HttpResponse response);

    @Override
    public boolean useAuthentication() {
        return false;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (this.useAuthentication() && !request.loggedIn()) {
            response.redirect("/index.html");
            return;
        }

        if (request.getMethod() == HttpMethod.GET) {
            this.doGet(request, response);
        }

        if (request.getMethod() == HttpMethod.POST) {
            this.doPost(request, response);
        }
    }
}
