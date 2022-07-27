package webserver.handler;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {

    HttpResponse handle(HttpRequest request);

}
