package http.controller;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class DefaultController extends PathController{

    public void doGet(HttpRequest request, HttpResponse response) {
        doGetDefault(request, response);
    }

}
