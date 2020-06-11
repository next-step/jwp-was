package http.controller;

import http.HttpMethod;
import http.HttpRequest;
import http.Response.HttpResponse;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public abstract class AbstractController implements Controller{

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (isGET(request)) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    private boolean isGET(HttpRequest request) {
        return HttpMethod.isGET(request.getMethod());
    }

    public void doPost(HttpRequest request, HttpResponse response){}
    public void doGet(HttpRequest request, HttpResponse response){}
}
