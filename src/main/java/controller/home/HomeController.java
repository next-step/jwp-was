package controller.home;

import controller.AbstractController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class HomeController extends AbstractController {

    public static final String URL = "/index.html";
    public static final String VIEW_PATH = "/index.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return HttpResponse.getView(VIEW_PATH);
    }
}
