package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.Users;
import view.TemplateView;

public class ListUserController extends AbstractController {

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        if (LoginChecker.isLogin(httpRequest)) {
            Users users = new Users();
            users.addUseraAll(DataBase.findAll());

            String templateViewString = readTemplateView("user/list", users);
            return HttpResponse.from(HttpStatus.OK, templateViewString .getBytes());
        }

        return HttpResponse.redirectBy302StatusCode("/user/login.html");
    }

    private String readTemplateView(String filePath, Object context) {
        TemplateView templateView = new TemplateView();
        return templateView.read(filePath, context);
    }
}
