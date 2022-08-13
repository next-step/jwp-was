package user.controller;

import exception.UnsupportedMethodException;
import user.service.RetrieveUserService;
import webserver.http.model.session.HttpSession;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;
import webserver.http.model.response.Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static constant.GlobalConstant.JSSESSION_ID;

public class ListUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.movePage(dispatch(httpRequest));
    }

    private Model dispatch(HttpRequest httpRequest) {
        if (isLogin(httpRequest)) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("users", RetrieveUserService.retrieveUsers());
            return new Model("user/list", modelMap);
        }
        return new Model("/user/login.html", null);
    }

    private boolean isLogin(HttpRequest httpRequest) {
        HttpSession httpSession = httpRequest.getHttpSession(JSSESSION_ID);
        if (httpSession == null) {
            return false;
        }
        String loginResult = (String) httpSession.getAttribute("login");
        return "true".equals(loginResult) && ControllerEnum.accessiblePagesAfterLogin(httpRequest);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        throw new UnsupportedMethodException();
    }
}
