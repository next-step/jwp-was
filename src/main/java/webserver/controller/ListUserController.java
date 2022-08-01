package webserver.controller;

import webserver.request.HttpRequest;
import webserver.request.Model;
import webserver.response.HttpResponse;
import webserver.service.UserService;
import webserver.session.HttpSession;
import webserver.session.HttpSessionFactory;

import java.io.IOException;

public class ListUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Model model = new Model();
        UserService requestService = new UserService(request);
        String sessionId = request.getCookie(HttpSession.SESSION_ID);
        HttpSession session = HttpSessionFactory.getSession(sessionId);
        boolean isLogin = (boolean) session.getAttribute(LOGINED_KEY);
        if (isLogin) {
            response.forward("user/list");
            return ;
        }
        model.set("users", requestService.findAllUser());
        response.addModel(model);
        try {
            response.forward(response, "user/list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
