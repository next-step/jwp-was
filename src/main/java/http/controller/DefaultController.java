package http.controller;

import http.HttpRequest;
/**
 * Created By kjs4395 on 2020-06-05
 */
public class DefaultController extends PathController{
    HttpRequest httpRequest;

    public DefaultController(HttpRequest httpRequest) {
        super(httpRequest);
    }
}
