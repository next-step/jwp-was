package http.controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public abstract class AbstractController implements Controller{

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (isGET(request)) {
            doGET(request, response);
        } else {
            doPOST(request, response);
        }
    }

    private boolean isGET(HttpRequest request) {
        return request.getMethod() == HttpMethod.GET;
    }

    protected abstract void doPOST(HttpRequest request, HttpResponse response);

    protected abstract void doGET(HttpRequest request, HttpResponse response);
}
