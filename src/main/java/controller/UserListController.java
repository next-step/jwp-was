package controller;

import db.DataBase;
import model.User;
import view.ViewResolver;
import webserver.Request;
import webserver.Response;
import webserver.response.HttpResponse;

public class UserListController extends AbstractController {

    private static final ViewResolver viewResolver = new ViewResolver();
    private static final String URL = "/user/list";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    Response doGet(Request request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return HttpResponse.redirect("/index.html");
        }

        String content = viewResolver.resolve("user/list", DataBase.findAll());
        return HttpResponse.ok(content.getBytes());
    }

    @Override
    Response doPost(Request request) {
        return HttpResponse.notFound();
    }
}
