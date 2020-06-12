package http.controller;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public interface Controller {

    void service(HttpRequest request, HttpResponse response);
}
