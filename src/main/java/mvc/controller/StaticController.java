package mvc.controller;

import http.HttpHeader;
import http.request.HttpRequest;
import http.request.protocol.Protocol;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import http.response.StatusLine;
import mvc.view.StaticViewResolver;
import mvc.view.View;
import mvc.view.ViewResolver;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class StaticController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        ViewResolver viewResolver = new StaticViewResolver();
        View view = viewResolver.resolveViewName(request.getPath());
        view.render(request, response);
    }
}
