package mvc.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import mvc.view.StaticViewResolver;
import mvc.view.View;
import mvc.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        ViewResolver viewResolver = new StaticViewResolver();
        View view = viewResolver.resolveViewName(request.getPath());
        view.render(request, response);
    }
}
