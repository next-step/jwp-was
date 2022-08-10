package webserver.controller;

import db.DataBase;
import webserver.http.model.Model;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ListUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Model model;
        if (isLogin(httpRequest)) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("users", DataBase.findAll());
            model = new Model("user/list", modelMap);
        } else {
            model = new Model("/user/login.html", null);
        }
        httpResponse.moveNotStaticResourcePage(httpResponse, model);
    }

    public boolean isLogin(HttpRequest httpRequest) {
        return ControllerEnum.accessiblePagesAfterLogin(httpRequest) && "logined=true".equals(httpRequest.getRequestHeaders().get("Cookie"));
    }
}
