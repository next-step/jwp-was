package user.controller;

import user.service.RetrieveUserService;
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
            modelMap.put("users", RetrieveUserService.retrieveUsers());
            model = new Model("user/list", modelMap);
        } else {
            model = new Model("/user/login.html", null);
        }
        httpResponse.movePage(model);
    }

    public boolean isLogin(HttpRequest httpRequest) {
        return ControllerEnum.accessiblePagesAfterLogin(httpRequest) && "logined=true".equals(httpRequest.getRequestHeaders().get("Cookie"));
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
