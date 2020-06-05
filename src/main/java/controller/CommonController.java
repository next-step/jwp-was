package controller;

import annotations.Controller;
import annotations.RequestMapping;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import utils.FileIoUtils;
import web.sevlet.View;
import web.sevlet.view.HtmlView;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class CommonController {

    @RequestMapping("/index.html")
    public View index() {
        return new HtmlView("./templates/index.html");
    }

    @RequestMapping("/user/form.html")
    public View userForm() {
        return new HtmlView("./templates/user/form.html");
    }

    @RequestMapping("/user/create")
    public View signUp(HttpRequest httpRequest) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        return new HtmlView("redirect:/index.html");
    }
}
