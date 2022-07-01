package mvc.controller;

import mvc.view.StaticViewResolver;
import mvc.view.View;
import mvc.view.ViewResolver;
import was.http.HttpRequest;
import was.http.HttpResponse;

public class StaticController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        ViewResolver viewResolver = new StaticViewResolver();
        View view = viewResolver.resolveViewName(request.getPath());
        view.render(request, response);
    }
}
