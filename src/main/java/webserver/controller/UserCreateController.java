package webserver.controller;

import webserver.domain.abstractController;

import java.util.HashMap;

public class UserCreateController extends abstractController {

    @Override
    public String setMethodType() {
        return "GET";
    }

    @Override
    public String setUrlPath() {
        return "/create";
    }

    @Override
    public String setReturnType() {
        return "";
    }

    @Override
    public String setReturnPath() {
        return "/index.html";
    }

    @Override
    public Object doAction(HashMap<String, String> param) {
        return "";
    }
}
