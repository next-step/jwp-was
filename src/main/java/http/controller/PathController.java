package http.controller;

import http.HttpRequest;
import http.HttpResponse;
import http.ResourcePathMaker;
import http.enums.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created By kjs4395 on 2020-06-05
 */
public abstract class PathController implements Controller{
    private static final Logger log = LoggerFactory.getLogger(PathController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if(request.getMethod().equals(Method.GET)) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    public void doGet(HttpRequest request, HttpResponse response)  {
        log.info("default controller get method execute ========");
        if(request.isStaticResource()) {
            response.addHeader("Content-Type", "text/css");
            response.forword(ResourcePathMaker.makeResourcePath(request.getPath()));
            return;
        }

        response.addHeader("Content-Type","text/html");
        response.forword(ResourcePathMaker.makeTemplatePath(request.getPath()));
    }

    public void doPost(HttpRequest request, HttpResponse response) {
        log.info("default controller post method execute ========");
    }
}
