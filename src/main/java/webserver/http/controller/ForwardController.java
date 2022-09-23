package webserver.http.controller;

import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;

public class ForwardController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward("./templates" + httpRequest.path());
    }
}
