package handler;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.StaticResourceView;
import http.view.TemplateModel;
import http.view.TemplateView;
import java.util.Optional;

public class UserListHandler implements Handler{

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        if(!isLogin(httpRequest)){
            return new HttpResponse(new TemplateView("login"));
        }

        TemplateModel templateModel = new TemplateModel();
        templateModel.add("users", DataBase.findAll());

        return new HttpResponse(new TemplateView("user/list", templateModel));
    }

    private boolean isLogin(HttpRequest httpRequest) {
        return Optional.ofNullable(httpRequest.getCookie("logined"))
            .filter(val->val.equals(Boolean.TRUE.toString()))
            .isPresent();
    }
}
